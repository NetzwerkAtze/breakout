package breakout.ui;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem {
    private List<Particle> particleList = new ArrayList<>();

    public void burst(double x, double y, Color color) {
        for (int i = 0; i < 50; i++) {
            double random = Math.random() * 360;
            double vxRandom = Math.cos(2 * Math.PI * random) * 2;
            double vyRandom = Math.sin(2 * Math.PI * random) * 2;
            int lifeSpanRandom = (int) Math.max((Math.random() * 50), 1);
            particleList.add(new Particle(x, y, vxRandom, vyRandom, lifeSpanRandom, color));
        }
    }
    public void update() {
        Iterator<Particle> it = particleList.iterator();
        while (it.hasNext()) {
            Particle particle = it.next();
            if (!particle.isAlive())
                it.remove();
            else
                particle.update();
        }
    }
    public List<Particle> getParticleList() {
        return particleList;
    }
}
