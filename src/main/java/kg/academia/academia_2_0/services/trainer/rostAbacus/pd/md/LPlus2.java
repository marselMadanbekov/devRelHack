package kg.academia.academia_2_0.services.trainer.rostAbacus.pd.md;

import kg.academia.academia_2_0.services.trainer.rostAbacus.TaskGenerator;

public class LPlus2 implements TaskGenerator {
    private LPlus2(){};

    private static class SingletonHolder{
        public static final LPlus2 HOLDER_INSTANCE = new LPlus2();
    }
    public static LPlus2 getInstance(){
        return LPlus2.SingletonHolder.HOLDER_INSTANCE;
    }
    @Override
    public int head(int sum, int leftNum) {
        boolean canAdd = leftNum < 9;
        switch (sum) {
            case 0:
                return random.nextInt(9) + 1;
            case 1:
                if (canAdd) {
                    if(random.nextInt(10) > 7) return random.nextInt(9) + 1;
                }
                if(random.nextInt(10)>2) return random.nextInt(3) + 5;
                return -1;
            case 2:
                if (canAdd) {
                    if(random.nextInt(10) > 6) return random.nextInt(2) + 8;
                }
                if(random.nextInt(10) > 5)  return random.nextInt(3) + 5;
                if (random.nextInt(10) > 3)
                    return random.nextInt(7) + 1;
                return -(random.nextInt(2) + 1);
            case 3:
                if (canAdd) {
                    if(random.nextInt(10) > 6) return random.nextInt(3) + 7;
                }
                if(random.nextInt(10) > 5)  return random.nextInt(3) + 5;
                if (random.nextInt(10) > 3)
                    return random.nextInt(6) + 1;
                return -(random.nextInt(3) + 1);
            case 4:
                if (canAdd) {
                    if(random.nextInt(10) > 5) return random.nextInt(4) + 6;
                }
                if (random.nextInt(10) > 3)
                    return random.nextInt(5) + 1;
                return -(random.nextInt(4) + 1);
            case 5:
                if (canAdd) {
                    if(random.nextInt(10) > 5) return random.nextInt(5) + 5;
                }
                if (random.nextInt(10) > 3)
                    return random.nextInt(4) + 1;
                return -(random.nextInt(5) + 1);
            case 6:
                if (canAdd) {
                    if(random.nextInt(10) > 5) return random.nextInt(6) + 4;
                }
                if (random.nextInt(10) > 3)
                    return random.nextInt(3) + 1;
                return -(random.nextInt(6) + 1);
            case 7:
                if (canAdd) {
                    if(random.nextInt(10) > 6) return random.nextInt(7) + 3;
                }
                if (random.nextInt(10) > 3)
                    return random.nextInt(2) + 1;
                return -(random.nextInt(7) + 1);
            case 8:
                if (canAdd) {
                    if(random.nextInt(10) > 4) return 2;
                    if(random.nextInt(10) > 5) return random.nextInt(8) + 2;
                }
                if (random.nextInt(10) > 3)
                    return 1;
                return -(random.nextInt(8) + 1);
            case 9:
                if (canAdd) {
                    if(random.nextInt(10) > 2) return 2;
                    if(random.nextInt(10) > 5) return random.nextInt(8) + 2;
                }
                return -(random.nextInt(9) + 1);
            default:
                return 0;
        }
    }

    @Override
    public int tail(int sum, int leftNum, boolean isPlus) {
        boolean canAdd = leftNum < 9;
        switch (sum) {
            case 0:
                if (isPlus) {
                    return random.nextInt(10);
                } else return 0;
            case 1:
                if (isPlus) {
                    if(canAdd){
                        if(random.nextInt(10) > 4)  return 9;
                    }
                    if(random.nextInt(10) > 4)  return random.nextInt(4) + 5;
                    return random.nextInt(9);
                } else return -(random.nextInt(2));
            case 2:
                if (isPlus) {
                    if(canAdd){
                        if(random.nextInt(10) > 5) return random.nextInt(2) + 8;
                    }
                    if(random.nextInt(10) > 4)  return random.nextInt(4) + 4;
                    return random.nextInt(8);
                } else return -(random.nextInt(3));
            case 3:
                if (isPlus) {
                    if(canAdd){
                        if(random.nextInt(10) > 6) return random.nextInt(3) + 7;
                    }
                    if(random.nextInt(10) > 4)  return random.nextInt(4) + 4;
                    return random.nextInt(7);
                } else return -(random.nextInt(4));
            case 4:
                if (isPlus) {
                    if(canAdd){
                        if(random.nextInt(10) > 5) return random.nextInt(4) + 6;
                    }
                    if(random.nextInt(10) > 4)  return random.nextInt(4) + 4;
                    return random.nextInt(6);
                } else return -(random.nextInt(5));
            case 5:
                if (isPlus) {
                    if(canAdd){
                        if(random.nextInt(10) > 7) return random.nextInt(5) + 5;
                    }
                    return random.nextInt(5);
                } else return -(random.nextInt(6));
            case 6:
                if (isPlus) {
                    if(canAdd){
                        if(random.nextInt(10) > 7) return random.nextInt(6) + 4;
                    }
                    return random.nextInt(4);
                } else return -(random.nextInt(7));
            case 7:
                if (isPlus) {
                    if(canAdd){
                        if(random.nextInt(10) > 7) return random.nextInt(7) + 3;
                    }
                    return random.nextInt(3);
                } else return -(random.nextInt(8));
            case 8:
                if (isPlus) {
                    if(canAdd){
                        if(random.nextInt(10) > 1) return 2;
                        if(random.nextInt(10) > 5) return random.nextInt(8) + 2;
                    }
                    return random.nextInt(2);
                } else return -(random.nextInt(9));
            case 9:
                if (isPlus) {
                    if(canAdd){
                        if(random.nextInt(10) > 4) return 2;
                        if(random.nextInt(10) > 5) return random.nextInt(8) + 2;
                    }
                    return 0;
                } else return -(random.nextInt(10));
            default:
                return 0;
        }
    }
}
