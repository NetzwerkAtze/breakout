package breakout.ui;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParticleSystem {
    private List<Particle> particleList = new ArrayList<>();

    public void burst(double x, double y, Color color) {
        int random = (int) (Math.random() * 10);
        for (int i = 0; i < random; i++) {
            double vxRandom = (Math.random() * 10);
            double vyRandom = (Math.random() * 10);
            int lifeSpanRandom = (int) (Math.random() * 10);
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
