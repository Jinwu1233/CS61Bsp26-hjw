import edu.princeton.cs.algs4.StdRandom;
import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        lifespan = LIFESPANS.getOrDefault(flavor, -1);
    }

    public Color color() {
        if (flavor == ParticleFlavor.EMPTY) {
            return Color.BLACK;
        }
        if (flavor == ParticleFlavor.SAND) {
            return Color.YELLOW;
        }
        if (flavor == ParticleFlavor.BARRIER) {
            return Color.GRAY;
        }
        if (flavor == ParticleFlavor.WATER) {
            return Color.BLUE;
        }
        if (flavor == ParticleFlavor.PLANT) {
            double ratio = (double) Math.max(0, Math.min(lifespan, PLANT_LIFESPAN)) / PLANT_LIFESPAN;
            int g = 120 + (int) Math.round((255 - 120) * ratio);
            return new Color(0, g, 0);
        }
        if (flavor == ParticleFlavor.FIRE) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FIRE_LIFESPAN)) / FIRE_LIFESPAN;
            int r = (int) Math.round(255 * ratio);
            return new Color(r, 0, 0);
        }
        if (flavor == ParticleFlavor.FOUNTAIN) {
            return Color.CYAN;
        }
        if (flavor == ParticleFlavor.FLOWER) {
            double ratio = (double) Math.max(0, Math.min(lifespan, FLOWER_LIFESPAN)) / FLOWER_LIFESPAN;
            int r = 120 + (int) Math.round((255 - 120) * ratio);
            int g = 70 + (int) Math.round((141 - 70) * ratio);
            int b = 80 + (int) Math.round((161 - 80) * ratio);
            return new Color(r, g, b);
        }
        return null;
    }

    public void moveInto(Particle other) {
        other.lifespan = lifespan;
        other.flavor = flavor;

        this.flavor = ParticleFlavor.EMPTY;
        this.lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        if(neighbors.get(Direction.DOWN).flavor == ParticleFlavor.EMPTY) {
            this.moveInto(neighbors.get(Direction.DOWN));
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        switch(StdRandom.uniformInt(3)) {
            case 0 : {
                return;
            }
            case 1 : {
                if(neighbors.get(Direction.LEFT).flavor != ParticleFlavor.EMPTY){
                    return;
                }else{
                    this.moveInto(neighbors.get(Direction.LEFT));
                }
            }
            case 2 : {
                if(neighbors.get(Direction.RIGHT).flavor != ParticleFlavor.EMPTY){
                    return;
                }else{
                    this.moveInto(neighbors.get(Direction.RIGHT));
                }
            }
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int result = StdRandom.uniformInt(10);
        if(result == 0 && neighbors.get(Direction.UP).flavor == ParticleFlavor.EMPTY){
            neighbors.get(Direction.UP).flavor = this.flavor;
            neighbors.get(Direction.UP).lifespan = LIFESPANS.get(neighbors.get(Direction.UP).flavor);
        }else if(result == 1 && neighbors.get(Direction.LEFT).flavor == ParticleFlavor.EMPTY){
            neighbors.get(Direction.LEFT).flavor = this.flavor;
            neighbors.get(Direction.LEFT).lifespan = LIFESPANS.get(neighbors.get(Direction.LEFT).flavor);
        }else if(result == 2 && neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.EMPTY){
            neighbors.get(Direction.RIGHT).flavor = this.flavor;
            neighbors.get(Direction.RIGHT).lifespan = LIFESPANS.get(neighbors.get(Direction.RIGHT).flavor);
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
        for (Particle neighbor : neighbors.values()) {
            if ((neighbor.flavor == ParticleFlavor.PLANT
                    || neighbor.flavor == ParticleFlavor.FLOWER)
                    && StdRandom.bernoulli(0.4)) {

                neighbor.flavor = ParticleFlavor.FIRE;
                neighbor.lifespan = FIRE_LIFESPAN;
            }
        }
    }

    public void action(Map<Direction, Particle> neighbors) {
        if(this.flavor == ParticleFlavor.EMPTY) {
            return;
        }
        if(this.flavor != ParticleFlavor.BARRIER) {
            this.fall(neighbors);
        }
        if(this.flavor == ParticleFlavor.WATER) {
            this.flow(neighbors);
        }
        if(this.flavor == ParticleFlavor.FLOWER || this.flavor == ParticleFlavor.PLANT) {
            this.grow(neighbors);
        }
        if(this.flavor == ParticleFlavor.FIRE) {
            this.burn(neighbors);
        }
    }

    public void decrementLifespan() {
        if (lifespan > 0) {
            lifespan--;
        }

        if (lifespan == 0) {
            flavor = ParticleFlavor.EMPTY;
            lifespan = -1;
        }
    }
}