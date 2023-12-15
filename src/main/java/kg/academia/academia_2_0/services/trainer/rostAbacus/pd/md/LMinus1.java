package kg.academia.academia_2_0.services.trainer.rostAbacus.pd.md;

import kg.academia.academia_2_0.services.trainer.rostAbacus.TaskGenerator;

public class LMinus1 implements TaskGenerator {
    private LMinus1(){};

    private static class SingletonHolder{
        public static final LMinus1 HOLDER_INSTANCE = new LMinus1();
    }
    public static LMinus1 getInstance(){
        return LMinus1.SingletonHolder.HOLDER_INSTANCE;
    }
    @Override
    public int head(int sum, int leftNum) {
        boolean canSubs = leftNum > 0;
        switch (sum) {
            case 0:
                if (canSubs) {
                    if (random.nextInt(10) > 0) return -1;
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return random.nextInt(9) + 1;
            case 1:
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                if(random.nextInt(10) > 6)  return -1;
                return random.nextInt(9) + 1;
            case 2:
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                if(random.nextInt(10) > 6)  return -(random.nextInt(2) + 1);
                return random.nextInt(9) + 1;
            case 3:
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                if(random.nextInt(10) > 6)  return -(random.nextInt(3) + 1);
                return random.nextInt(9) + 1;
            case 4:
                if (canSubs) {
                    if (random.nextInt(10) > 3) return -(random.nextInt(9) + 1);
                }
                if(random.nextInt(10) > 3)  return -(random.nextInt(4) + 1);
                return random.nextInt(9) + 1;
            case 5:
                if (canSubs) {
                    if (random.nextInt(10) > 3) return -(random.nextInt(9) + 1);
                }
                if(random.nextInt(10) > 3)  return -(random.nextInt(5) + 1);
                return random.nextInt(9) + 1;
            case 6:
                if (canSubs) {
                    if (random.nextInt(10) > 3) return -(random.nextInt(9) + 1);
                }
                if(random.nextInt(10) > 3)  return -(random.nextInt(6) + 1);
                return random.nextInt(9) + 1;
            case 7:
                if (canSubs) {
                    if (random.nextInt(10) > 3) return -(random.nextInt(9) + 1);
                }
                if(random.nextInt(10) > 3)  return -(random.nextInt(7) + 1);
                return random.nextInt(9) + 1;
            case 8:
                if (canSubs) {
                    if (random.nextInt(10) > 3) return -(random.nextInt(9) + 1);
                }
                if(random.nextInt(10) > 6)  return -(random.nextInt(8) + 1);
                return random.nextInt(9) + 1;
            case 9:
                if (canSubs) {
                    if (random.nextInt(10) > 3) return -(random.nextInt(9) + 1);
                }
                if(random.nextInt(10) > 3)  return -(random.nextInt(9) + 1);
                return random.nextInt(9) + 1;
            default:
                return 0;
        }
    }

    @Override
    public int tail(int sum, int leftNum, boolean isPlus) {
        boolean canSubs = leftNum > 0;
        switch (sum) {
            case 0:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 0) return -1;
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return 0;
            case 1:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return -random.nextInt(2);
            case 2:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return -random.nextInt(3);
            case 3:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return -random.nextInt(4);
            case 4:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return -random.nextInt(5);
            case 5:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return -random.nextInt(6);
            case 6:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return -random.nextInt(7);
            case 7:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return -random.nextInt(8);
            case 8:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(8) + 2);
                }
                return -random.nextInt(9);
            case 9:
                if (isPlus) return random.nextInt(10);
                if (canSubs) {
                    if (random.nextInt(10) > 6) return -(random.nextInt(9) + 1);
                }
                return -random.nextInt(10);
            default:
                return 0;
        }
    }
}
