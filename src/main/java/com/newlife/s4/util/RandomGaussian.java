package com.newlife.s4.util;

import java.util.ArrayList;
import java.util.List;

public class RandomGaussian {
    private double mean;
    private double stddev;
    private boolean valid = false;
    private double next = 0;

    RandomGaussian(double mean, double stddev) {
        this.mean = mean;
        this.stddev = stddev;
    }

    public double rand() {
        if (valid) {
            valid = false;
            return next;
        } else {
            valid = true;
            double[] r = gaussian();
            next = r[1];
            return r[0];
        }
    }

    private double[] gaussian() {
        double theta = 2 * Math.PI * Math.random();
        double rho = Math.sqrt(-2 * Math.log(1 - Math.random()));
        double scale = stddev * rho;
        double x = mean + scale * Math.cos(theta);
        double y = mean + scale * Math.sin(theta);
        return new double[] { x, y };
    }

    /**
     * 正态分布
     * @param money
     * @param count
     * @return
     */
    public static List<Integer> generateGDRedPacket(int money,int count) {
        int mon = money , pics = count;
        List<Integer> valueVec = new ArrayList<>(pics);
        if (pics == 1) {
            System.out.println(new int[] { mon });
        } else {

            int moneyLeft = mon - pics*30;
            for (int i = 0; i < pics-1; i++) {
                int mu = moneyLeft / (pics - i);
                int sigma = mu / 2;
                if (i == pics - 1) {
                    valueVec.add(moneyLeft + pics);
                } else {
                    RandomGaussian randomGaussian = new RandomGaussian(mu,
                            sigma);
                    double noiseValue = randomGaussian.rand();

                    if (noiseValue < 0){
                        noiseValue = 0;
                    }
                    if (noiseValue > moneyLeft) {
                        noiseValue = moneyLeft;
                    }

                    int ss = Integer.valueOf(String.format("%.0f", noiseValue));

                    valueVec.add(Integer.valueOf(ss)+30);
                    moneyLeft -= ss;
                }

            }
            valueVec.add(mon - valueVec.stream().mapToInt(Integer::intValue).sum());
           }
           return valueVec;
    }

    public static void main(String[] args) {
        int mon = 9*100 , pics = 10;
        List<Integer> valueVec = new ArrayList<>(pics);
        if (pics == 1) {
            System.out.println(new int[] { mon });
        } else {

            int moneyLeft = mon - pics*30;
            for (int i = 0; i < pics-1; i++) {
                int mu = moneyLeft / (pics - i);
                int sigma = mu / 2;
                if (i == pics - 1) {
                    valueVec.add(moneyLeft + pics);
                } else {
                    RandomGaussian randomGaussian = new RandomGaussian(mu,
                            sigma);
                    double noiseValue = randomGaussian.rand();

                    if (noiseValue < 0){
                        noiseValue = 0;
                    }
                    if (noiseValue > moneyLeft) {
                        noiseValue = moneyLeft;
                    }

                    int ss = Integer.valueOf(String.format("%.0f", noiseValue));

                    valueVec.add(Integer.valueOf(ss)+30);
                    moneyLeft -= ss;
                }

            }
            valueVec.add(mon - valueVec.stream().mapToInt(Integer::intValue).sum());
            for (int i = 0; i < valueVec.size(); i++) {
                int v = valueVec.get(i);
                String s = "";
                if ((v + "").length() < 2) {
                    s = "  " + v;
                } else if ((v + "").length() < 3) {
                    s = " " + v;
                } else {
                    s = "" + v;
                }
                if(i%10==0)
                    System.out.println();
                System.out.print("value:" + s+"|");
            }
            System.out
                    .println("===================================================");
            System.out.println(valueVec.stream().mapToInt(Integer::intValue)
                    .sum());

        }
    }
}
