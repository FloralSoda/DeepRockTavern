package net.hydroxa.drgbeer.sound;

import net.hydroxa.drgbeer.DRGBeerMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static SoundEvent FART = registerSoundEvent("fart");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = new Identifier(DRGBeerMod.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }

    public static void registerSoundEvents() {
        DRGBeerMod.LOGGER.info("Registering sound events");
    }
}
