package xyz.soydev.features;

import com.mojang.serialization.Codec;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import xyz.soydev.JacClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Feature {
    private static final String FEATURE_PREFIX = "jac.feature.";
    private final String CATEGORY_PREFIX = "jac.category.";

    private final String name;
    private final String category;
    private final List<JacKeyBinding> keyBindings = new ArrayList<>();
    private final SimpleOption<Boolean> enabled;
    private final List<SimpleOption<?>> options = new ArrayList<>();
    public Feature(String name, String category) {
        this.name = name;
        this.category = category;
        enabled = addBoolOption("enabled", false);
    }
    public void toggle() {
        enabled.setValue(!enabled.getValue());
        message(Text.translatable("jac.feature.togglemessage").getString() + (isEnabled() ? " on" : " off"));
    }
    protected void message(String message) {
        ClientPlayerEntity player = JacClient.instance.player;
        if (player == null) return;

        message = isEnabled() ? ("§a" + message.replace("§r", "§a")) : ("§c" + message.replace("§r", "§c"));
        player.sendMessage(Text.of("[JAC] " + name + ": " + message), true);
    }
    /* Runs every tick */
    protected abstract void onTick();
    /* Runs every frame */
    protected abstract void onFrame();
    public boolean isEnabled() {
        return enabled.getValue();
    }

    protected KeyBinding addKeyBinding(String keySuffix, int glfwKey, Runnable callback) {
        JacKeyBinding keyBinding = new JacKeyBinding("jac.key." + name + keySuffix, glfwKey, CATEGORY_PREFIX + category);
        if (callback != null) keyBinding.setCallback(callback);
        keyBindings.add(keyBinding);
        return keyBinding;
    }

    protected KeyBinding addToggleKeyBinding(int glfwKey) {
        JacKeyBinding keyBinding = new JacKeyBinding("jac.key." + name + ".toggle", glfwKey, CATEGORY_PREFIX + category);
        keyBinding.setCallback(()->{
            if (!keyBinding.repeat()) {
                toggle();
            }
        });
        keyBindings.add(keyBinding);
        return keyBinding;
    }

    public List<JacKeyBinding> getKeyBindings() {
        return keyBindings;
    }

    public String getName() {
        return name;
    }

    public Text getTitle() {
        return Text.translatable(FEATURE_PREFIX + getName());
    }

    public List<SimpleOption<?>> getOptions() {
        return options;
    }

    protected SimpleOption<Boolean> addBoolOption(String name, boolean defaultValue) {
        String optionName = FEATURE_PREFIX + this.name + ".option." + name;
        SimpleOption<Boolean> option = SimpleOption.ofBoolean(
                optionName,
                //SimpleOption.constantTooltip(Text.translatable(optionName + ".tooltip")),
                SimpleOption.emptyTooltip(),
                defaultValue
        );
        options.add(option);

        return  option;
    }


    protected SimpleOption<Integer> addIntegerOption(String name, int defaultValue, int min, int max) {
        String optionName = FEATURE_PREFIX + this.name + ".option." + name;
        SimpleOption<Integer> option = new SimpleOption<>(
                optionName,
                //SimpleOption.constantTooltip(Text.translatable(optionName + ".tooltip")),
                SimpleOption.emptyTooltip(),
                (text, integer) -> { return Text.translatable(optionName).append(": " + integer); },
                new SimpleOption.ValidatingIntSliderCallbacks(min, max),
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
    protected SimpleOption<Double> addDoubleOption(String name, double defaultValue, double min, double max) {
        String optionName = FEATURE_PREFIX + this.name + ".option." + name;
        SimpleOption<Double> option = new SimpleOption<>(
                optionName,
                //SimpleOption.constantTooltip(Text.translatable(optionName + ".tooltip")),
                SimpleOption.emptyTooltip(),
                (text, double_) -> { return Text.translatable(optionName).append(": " + double_); },
                SimpleOption.DoubleSliderCallbacks.INSTANCE.withModifier(
                        double_ -> Math.max(double_, min), double_ -> double_ * max
                ),
                defaultValue,
                (double_) -> {}
        );

        options.add(option);

        return option;
    }
}
