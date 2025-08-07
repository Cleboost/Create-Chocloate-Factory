package fr.cleboost.createchocolatefactory.core;

import fr.cleboost.createchocolatefactory.CreateChocolateFactory;
import fr.cleboost.createchocolatefactory.utils.Taste;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class CCFRegistryKeys {
    public static final ResourceKey<Registry<Taste>> TASTE_REGISTRY_KEY = ResourceKey.createRegistryKey(CreateChocolateFactory.asResource("taste"));
}
