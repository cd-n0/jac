package xyz.soydev.jac.features;

import xyz.soydev.jac.JacClient;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import xyz.soydev.jac.events.JacEventBus;
import xyz.soydev.jac.keymap.JacKeyMapping;
import xyz.soydev.jac.keymap.JacKeyMappingCallback;

public abstract class Feature {
    private static final String FEATURE_PREFIX = "jac.feature.";
    private final String CATEGORY_PREFIX = "jac.category.";

    private final String name;
    private final String category;
    private final List<JacKeyMapping> keyBindings = new ArrayList<>();
    private boolean enabled = false;
    private final OptionInstance<Boolean> enabledOption;
    private final List<OptionInstance<?>> options = new ArrayList<>();
    public Feature(String name, String category, boolean enabled) {
        this.name = name;
        this.category = category;
        this.enabled = enabled;
        enabledOption = addBoolOption("enabled", enabled);
        if (enabled) JacEventBus.get().register(this);
    }

    public void toggle() {
        // FIXME: Only bindings register/unregister
        //  somehow watch changes on the enabledOption to do it as well
        // TODO: Also options being here is so stupid just remove them from this class
        if (enabled) {
            disable();
        } else {
            enable();
        }
        message(Component.translatable("jac.feature.togglemessage").getString() + (isEnabled() ? " on" : " off"));
    }
    protected void message(String message) {
        LocalPlayer player = JacClient.instance.player;
        if (player == null) return;

        message = isEnabled() ? ("§a" + message.replace("§r", "§a")) : ("§c" + message.replace("§r", "§c"));
        player.displayClientMessage(Component.nullToEmpty("[JAC] " + name + ": " + message), true);
    }

    public void enable() {
        if (enabled) return;
        enabled = true;
        enabledOption.set(true);
        JacEventBus.get().register(this);
    }

    public void disable() {
        if (enabled == false) return;
        enabled = false;
        enabledOption.set(false);
        JacEventBus.get().unregister(this);
    }

    public boolean isEnabled() {
        return enabled;
    }

    protected KeyMapping addKeyBinding(String keySuffix, int glfwKey, JacKeyMappingCallback callback) {
        JacKeyMapping keyBinding = new JacKeyMapping("jac.key." + name + keySuffix, glfwKey, CATEGORY_PREFIX + category);
        if (callback != null) keyBinding.setCallback(callback);
        keyBindings.add(keyBinding);
        return keyBinding;
    }

    protected KeyMapping addToggleKeyBinding(int glfwKey) {
        JacKeyMapping keyBinding = new JacKeyMapping("jac.key." + name + ".toggle", glfwKey, CATEGORY_PREFIX + category);
        keyBinding.setCallback(this::toggle);
        keyBindings.add(keyBinding);
        return keyBinding;
    }

    public List<JacKeyMapping> getKeyBindings() {
        return keyBindings;
    }

    public String getName() {
        return name;
    }

    public Component getTitle() {
        return Component.translatable(FEATURE_PREFIX + getName());
    }

    public List<OptionInstance<?>> getOptions() {
        return options;
    }

    protected OptionInstance<Boolean> addBoolOption(String name, boolean defaultValue) {
        String optionName = FEATURE_PREFIX + this.name + ".option." + name;
        OptionInstance<Boolean> option = OptionInstance.createBoolean(
                optionName,
                //OptionInstance.cachedConstantTooltip(Component.translatable(optionName + ".tooltip")),
                OptionInstance.noTooltip(),
                defaultValue
        );
        options.add(option);

        return  option;
    }


    protected OptionInstance<Integer> addIntegerOption(String name, int defaultValue, int min, int max) {
        String optionName = FEATURE_PREFIX + this.name + ".option." + name;
        OptionInstance<Integer> option = new OptionInstance<>(
                optionName,
                //OptionInstance.cachedConstantTooltip(Component.translatable(optionName + ".tooltip")),
                OptionInstance.noTooltip(),
                (text, integer) -> { return Component.translatable(optionName).append(": " + integer); },
                new OptionInstance.IntRange(min, max),
                defaultValue,
                (integer) -> {}
        );
        options.add(option);

        return option;
    }

//    public static record ValidatingIntSliderCallbacks(int comp_593, int comp_594, boolean comp_2661) implements SliderCallbacks<Double> {
//        public ValidatingIntSliderCallbacks(int i, int j) {
//            this(i, j, true);
//        }
//
//        public Optional<Integer> validate(Integer integer) {
//            return integer.compareTo(this.comp_593()) >= 0 && integer.compareTo(this.comp_594()) <= 0 ? Optional.of(integer) : Optional.empty();
//        }
//
//        public Codec<Integer> comp_675() {
//            return Codec.intRange(this.comp_593, this.comp_594 + 1);
//        }
//    }
    // TODO: handle negatives
    protected OptionInstance<Double> addDoubleOption(String name, double defaultValue, double min, double max) {
        String optionName = FEATURE_PREFIX + this.name + ".option." + name;
        OptionInstance<Double> option = new OptionInstance<>(
                optionName,
                //OptionInstance.cachedConstantTooltip(Component.translatable(optionName + ".tooltip")),
                OptionInstance.noTooltip(),
                (text, double_) -> { return Component.translatable(optionName).append(": " + double_); },
                OptionInstance.UnitDouble.INSTANCE.xmap(
                        double_ -> Math.max(double_, min), double_ -> double_ * max
                ),
                defaultValue,
                (double_) -> {}
        );

        options.add(option);

        return option;
    }
}
