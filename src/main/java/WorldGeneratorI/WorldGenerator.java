package WorldGeneratorI;

import org.terasology.world.generator.RegisterWorldGenerator;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.engine.SimpleUri;
import org.terasology.registry.In;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.plugin.WorldGeneratorPluginLibrary;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;

@RegisterWorldGenerator(id = "WorldGenI", displayName = "World Generator I")
public class WorldGenerator extends BaseFacetedWorldGenerator
{
    public WorldGenerator(SimpleUri uri)
    {
        super(uri);
    }

    @In
    private WorldGeneratorPluginLibrary worldGenPluginLib;

    @Override
    protected WorldBuilder createWorld()
    {
        return new WorldBuilder(worldGenPluginLib)
                    .addRasterizer(new WorldGenIRasterizer())
                    .addProvider(new SurfaceProvider())
                    .addProvider(new SeaLevelProvider(0));
    }
}
