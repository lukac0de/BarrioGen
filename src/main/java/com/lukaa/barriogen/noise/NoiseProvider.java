package com.lukaa.barriogen.noise;

public class NoiseProvider {

    private final long seed;

    public NoiseProvider(long seed) {
        this.seed = seed;
    }

    // Noise 2D simple usando Math — sin librerías externas
    public double sample2D(String domain, double x, double z) {
        long domainSeed = seed ^ (domain.hashCode() * 2654435761L);
        return gradientNoise(domainSeed, x, z);
    }

    public double sampleFBM(String domain, double x, double z, int octaves) {
        double val = 0, amp = 1, freq = 1, max = 0;
        for (int i = 0; i < octaves; i++) {
            long s = seed ^ ((domain.hashCode() + i) * 2654435761L);
            val += gradientNoise(s, x * freq, z * freq) * amp;
            max += amp;
            amp  *= 0.5;
            freq *= 2.0;
        }
        return val / max;
    }

    private double gradientNoise(long s, double x, double z) {
        int xi = (int) Math.floor(x);
        int zi = (int) Math.floor(z);
        double fx = x - xi;
        double fz = z - zi;
        double ux = fade(fx);
        double uz = fade(fz);

        double n00 = dot(grad(hash(s, xi,     zi)),     fx,       fz);
        double n10 = dot(grad(hash(s, xi + 1, zi)),     fx - 1.0, fz);
        double n01 = dot(grad(hash(s, xi,     zi + 1)), fx,       fz - 1.0);
        double n11 = dot(grad(hash(s, xi + 1, zi + 1)), fx - 1.0, fz - 1.0);

        return lerp(uz,
                lerp(ux, n00, n10),
                lerp(ux, n01, n11));
    }

    private double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private int[] grad(int hash) {
        int h = hash & 3;
        return switch (h) {
            case 0  -> new int[]{ 1,  1};
            case 1  -> new int[]{-1,  1};
            case 2  -> new int[]{ 1, -1};
            default -> new int[]{-1, -1};
        };
    }

    private double dot(int[] g, double x, double z) {
        return g[0] * x + g[1] * z;
    }

    private int hash(long s, int x, int z) {
        long h = s ^ (x * 374761393L) ^ (z * 668265263L);
        h = (h ^ (h >>> 13)) * 1274126177L;
        return (int)(h ^ (h >>> 16)) & 0xFF;
    }
}