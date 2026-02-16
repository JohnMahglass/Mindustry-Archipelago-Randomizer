package mindustry.randomizer;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.Planets;
import mindustry.content.SectorPresets;
import mindustry.ctype.UnlockableContent;
import mindustry.randomizer.techtree.ApLocation;
import mindustry.type.Planet;
import mindustry.type.SectorPreset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mindustry.Vars.state;

public class ComplexApLocations {

    private static final List<UnlockableContent> sharedRessources = new ArrayList<>(
        Arrays.asList(Items.graphite, Liquids.water, Items.sand, Items.silicon, Liquids.slag, Items.thorium, Items.surgeAlloy, Items.phaseFabric)
    );

    // Ressources
    public ApLocation serpuloGraphite;
    public ApLocation erekirGraphite;

    public ApLocation serpuloWater;
    public ApLocation erekirWater;

    public ApLocation serpuloSand;
    public ApLocation erekirSand;

    public ApLocation serpuloSilicon;
    public ApLocation erekirSilicon;

    public ApLocation serpuloSlag;
    public ApLocation erekirSlag;

    public ApLocation serpuloThorium;
    public ApLocation erekirThorium;

    public ApLocation serpuloSurgeAlloy;
    public ApLocation erekirSurgeAlloy;

    public ApLocation serpuloPhaseFabric;
    public ApLocation erekirPhaseFabric;

    // Sector
    public ApLocation serpuloFrozenForest;
    public ApLocation erekirAegis;

    public ComplexApLocations() {
        // Ressources
        this.serpuloGraphite = new ApLocation("ap-serpulo-graphite", "Graphite", Items.graphite, 223L);
        this.erekirGraphite = new ApLocation("ap-erekir-graphite", "Graphite", Items.graphite, 510L);

        this.serpuloWater = new ApLocation("ap-serpulo-water", "Water", Liquids.water, 211L);
        this.erekirWater = new ApLocation("ap-erekir-water", "Water", Liquids.water, 504L);

        this.serpuloSand = new ApLocation("ap-serpulo-sand", "Sand", Items.sand, 219L);
        this.erekirSand = new ApLocation("ap-erekir-sand", "Sand", Items.sand, 501L);

        this.serpuloSilicon = new ApLocation("ap-serpulo-silicon", "Silicon", Items.silicon, 224L);
        this.erekirSilicon = new ApLocation("ap-erekir-silicon", "Silicon", Items.silicon, 502L);

        this.serpuloSlag = new ApLocation("ap-serpulo-slag", "Slag", Liquids.slag, 221L);
        this.erekirSlag = new ApLocation("ap-erekir-slag", "Slag", Liquids.slag, 512L);

        this.serpuloThorium = new ApLocation("ap-serpulo-thorium", "Thorium", Items.thorium, 215L);
        this.erekirThorium = new ApLocation("ap-erekir-thorium", "Thorium", Items.thorium, 514L);

        this.serpuloSurgeAlloy = new ApLocation("ap-serpulo-surge-alloy", "Surge alloy", Items.surgeAlloy, 216L);
        this.erekirSurgeAlloy = new ApLocation("ap-erekir-surge-alloy", "Surge alloy", Items.surgeAlloy, 516L);

        this.serpuloPhaseFabric = new ApLocation("ap-serpulo-phase-fabric", "Phase fabric", Items.phaseFabric, 217L);
        this.erekirPhaseFabric = new ApLocation("ap-erekir-phase-fabric", "Phase fabric", Items.phaseFabric, 517L);


        // Sector
        this.serpuloFrozenForest = new ApLocation("ap-frozen-forest", "Frozen Forest", SectorPresets.frozenForest, 181L);
        this.erekirAegis = new ApLocation("ap-aegis", "Aegis", SectorPresets.aegis, 451L);
    }

    public List<UnlockableContent> getSharedRessources() {
        return sharedRessources;
    }

    public boolean isSharedRessource(UnlockableContent content) {
        return sharedRessources.contains(content);
    }

    public void unlockSharedRessource(UnlockableContent content) {
        Planet currentPlanet = state.getPlanet();
        if (content == Items.graphite) {
            if (currentPlanet == Planets.erekir) {
                erekirGraphite.quietUnlock();
            } else if (currentPlanet == Planets.serpulo) {
                serpuloGraphite.quietUnlock();
            }
        }
        if (content == Liquids.water) {
            if (currentPlanet == Planets.erekir) {
                erekirWater.quietUnlock();
            } else if (currentPlanet == Planets.serpulo) {
                serpuloWater.quietUnlock();
            }
        }
        if (content == Items.sand) {
            if (currentPlanet == Planets.erekir) {
                erekirSand.quietUnlock();
            } else if (currentPlanet == Planets.serpulo) {
                serpuloSand.quietUnlock();
            }
        }
        if (content == Items.silicon) {
            if (currentPlanet == Planets.erekir) {
                erekirSilicon.quietUnlock();
            } else if (currentPlanet == Planets.serpulo) {
                serpuloSilicon.quietUnlock();
            }
        }
        if (content == Liquids.slag) {
            if (currentPlanet == Planets.erekir) {
                erekirSlag.quietUnlock();
            } else if (currentPlanet == Planets.serpulo) {
                serpuloSlag.quietUnlock();
            }
        }
        if (content == Items.thorium) {
            if (currentPlanet == Planets.erekir) {
                erekirThorium.quietUnlock();
            } else if (currentPlanet == Planets.serpulo) {
                serpuloThorium.quietUnlock();
            }
        }
        if (content == Items.surgeAlloy) {
            if (currentPlanet == Planets.erekir) {
                erekirSurgeAlloy.quietUnlock();
            } else if (currentPlanet == Planets.serpulo) {
                serpuloSurgeAlloy.quietUnlock();
            }
        }
        if (content == Items.phaseFabric) {
            if (currentPlanet == Planets.erekir) {
                erekirPhaseFabric.quietUnlock();
            } else if (currentPlanet == Planets.serpulo) {
                serpuloPhaseFabric.quietUnlock();
            }
        }
    };
}
