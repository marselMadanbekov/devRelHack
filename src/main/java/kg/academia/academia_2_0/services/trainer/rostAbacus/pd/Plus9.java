package kg.academia.academia_2_0.services.trainer.rostAbacus.pd;


import kg.academia.academia_2_0.services.trainer.rostAbacus.TaskGenerator;

public class Plus9 implements TaskGenerator {
    private Plus9(){};

    private static class SingletonHolder{
        public static final Plus9 HOLDER_INSTANCE = new Plus9();
    }
    public static Plus9 getInstance(){
        return Plus9.SingletonHolder.HOLDER_INSTANCE;
    }
    @Override
    public int head(int sum,int leftNum) {
        switch (sum) {
            case 0:
                if (random.nextInt(10) > 2) return random.nextInt(5) + 5;
                return random.nextInt(4) + 1;
            case 1:
                if (random.nextInt(10) > 4) {
                    if (random.nextInt(10) > 4) return 9;
                    return random.nextInt(9) + 1;
                } else return -1;
            case 2:
                if (random.nextInt(10) > 4) {
                    if (random.nextInt(10) > 4) return 9;
                    return random.nextInt(7) + 1;
                } else return -(random.nextInt(2) + 1);
            case 3:
                if (random.nextInt(10) > 5) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(6) + 1;
                } else return -(random.nextInt(2) + 1);
            case 4:
                if (random.nextInt(10) > 5) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(5) + 1;
                } else return -(random.nextInt(4) + 1);
            case 5:
                if (random.nextInt(10) > 5) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(4) + 1;
                } else return -(random.nextInt(5) + 1);
            case 6:
                if (random.nextInt(10) > 5) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(3) + 1;
                } else return -(random.nextInt(6) + 1);
            case 7:
                if (random.nextInt(10) > 5) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(2) + 1;
                } else return -(random.nextInt(7) + 1);
            case 8:
                if (random.nextInt(10) > 5) {
                    if (random.nextInt(10) > 6) return 9;
                    return 1;
                } else return -(random.nextInt(8) + 1);
            case 9:
                if (random.nextInt(10) > 5) {
                    if (random.nextInt(10) > 6) return 9;
                    return 0;
                } else return -(random.nextInt(9) + 1);
            default:
                return 0;
        }
    }

    @Override
    public int tail(int sum,int leftNum, boolean isPlus) {
        switch (sum) {
            case 0:
                if (isPlus) {
                    if (random.nextInt(10) > 3) return random.nextInt(5) + 5;
                    return random.nextInt(5);
                } else return 0;
            case 1:
                if (isPlus) {
                    if (random.nextInt(10) > 2) return 9;
                    return random.nextInt(10);
                } else return -(random.nextInt(2));
            case 2:
                if (isPlus) {
                    if (random.nextInt(10) > 3) return 9;
                    return random.nextInt(8);
                } else return -(random.nextInt(3));
            case 3:
                if (isPlus) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(7);
                } else return -(random.nextInt(4));
            case 4:
                if (isPlus) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(6);
                } else return -(random.nextInt(5));
            case 5:
                if (isPlus) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(5);
                } else return -(random.nextInt(6));
            case 6:
                if (isPlus) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(4);
                } else return -(random.nextInt(7));
            case 7:
                if (isPlus) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(3);
                } else return -(random.nextInt(8));
            case 8:
                if (isPlus) {
                    if (random.nextInt(10) > 6) return 9;
                    return random.nextInt(2);
                } else return -(random.nextInt(9));
            case 9:
                if (isPlus) {
                    if (random.nextInt(10) > 4) return 9;
                    return 0;
                } else return -(random.nextInt(10));
            default:
                return 0;
        }
    }
}
