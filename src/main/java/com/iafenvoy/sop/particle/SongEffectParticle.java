package com.iafenvoy.sop.particle;

import com.iafenvoy.neptune.util.RandomHelper;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SongEffectParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteSet;

    protected SongEffectParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, SpriteProvider sprite) {
        super(clientWorld, d, e, f);
        this.spriteSet = sprite;
        this.setVelocity(RandomHelper.nextDouble(-0.1, 0.1), RandomHelper.nextDouble(-0.1, 0.1), RandomHelper.nextDouble(-0.1, 0.1));
        this.setSpriteForAge(sprite);
        this.setBoundingBoxSpacing(0.2F, 0.2F);
        this.maxAge = 14 + this.random.nextInt(42);
        this.gravityStrength = 0.0F;
        this.collidesWithWorld = false;
        this.setColor((float) g, (float) h, (float) i);
    }

    @Override
    public int getBrightness(float partialTick) {
        return 15728880;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.dead)
            this.setSprite(this.spriteSet.getSprite(this.age / 2 % 8 + 1, 8));
    }

    public static ParticleFactory<DefaultParticleType> create(SpriteProvider sprite) {
        return (parameters, world, x, y, z, r, g, b) -> new SongEffectParticle(world, x, y, z, r, g, b, sprite);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }
}
