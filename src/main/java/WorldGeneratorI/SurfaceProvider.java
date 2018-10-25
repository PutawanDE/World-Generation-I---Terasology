package WorldGeneratorI;

import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.Produces;
import org.terasology.world.generation.facets.SurfaceHeightFacet;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.utilities.procedural.Noise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.utilities.procedural.SubSampledNoise;
import org.terasology.math.geom.BaseVector2i;
import org.terasology.math.geom.Rect2i;
import org.terasology.math.geom.Vector2f;
import org.terasology.world.generation.Border3D;

@Produces(SurfaceHeightFacet.class)
public class SurfaceProvider implements FacetProvider
{
    private Noise surfaceNoise;

    @Override
    public void setSeed (long seed)
    {
        //Create surface noise by subsampling (lerps between noise values) to get various noises values
        surfaceNoise = new SubSampledNoise(new SimplexNoise(seed), new Vector2f(0.01f,0.01f), 1);
    }

    @Override
    public void process(GeneratingRegion region)
    {
        //Create surface height facet
        Border3D border = region.getBorderForFacet(SurfaceHeightFacet.class);
        SurfaceHeightFacet facet = new SurfaceHeightFacet(region.getRegion(), border);

        //Loop through every position, modify facet using the surface noise
        Rect2i processRegion = facet.getWorldRegion();
        for (BaseVector2i position : processRegion.contents())
        {
            facet.setWorld(position, surfaceNoise.noise(position.x(), position.y()) * 20);
        }

        //Pass new created facet (from loop) to the region
        region.setRegionFacet(SurfaceHeightFacet.class, facet);
    }
}
