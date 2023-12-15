package kg.academia.academia_2_0.services.trainer.rostAbacus.pb;


import kg.academia.academia_2_0.services.trainer.rostAbacus.TaskGenerator;

public class Minus3 implements TaskGenerator {
    private Minus3(){};

    private static class SingletonHolder{
        public static final Minus3 HOLDER_INSTANCE = new Minus3();
    }
    public static Minus3 getInstance(){
        return Minus3.SingletonHolder.HOLDER_INSTANCE;
    }
    public int head(int sum, int leftNum){
        switch (sum){
            case 0:
                if(random.nextInt(5) <= 2)    return random.nextInt(5) + 5;
                return random.nextInt(4) + 1;
            case 1:
                if(random.nextInt(5) <= 2)    return random.nextInt(4) + 5;
                return random.nextInt(3) + 1;
            case 2:
                if(random.nextInt(5) <= 2)    return random.nextInt(3) + 5;
                return random.nextInt(3) + 1;
            case 3:
                if(random.nextInt(5) <= 2)    return random.nextInt(2) + 5;
                return random.nextInt(3) + 1;
            case 4:
                if(random.nextInt(5) <= 2)    return 5;
                return random.nextInt(3) + 1;
            case 5:
                if(random.nextInt(5) <= 2)    return -3;
                else{
                    if(random.nextBoolean()) return -(random.nextInt(3) + 1);
                    return -5;
                }
            case 6:
                if(random.nextInt(5) <= 2)    return -3;
                else{
                    if(random.nextBoolean()) return -(random.nextInt(3) + 1);
                    return -(random.nextInt(2) + 5);
                }
            case 7:
                if(random.nextInt(5) <= 2)    return -3;
                else{
                    if(random.nextBoolean()) return -(random.nextInt(3) + 1);
                    return -(random.nextInt(3) + 5);
                }
            case 8:
                if(random.nextBoolean()) return -(random.nextInt(3) + 1);
                return -(random.nextInt(4) + 5);
            case 9:
                if(random.nextBoolean())  return -(5 + random.nextInt(5));
                return -(random.nextInt(4) + 1);

        }
        return 0;
    }
    public int tail(int sum, int leftNum, boolean isPlus){
        switch (sum){
            case 0:
                if(isPlus){
                    if(random.nextInt(5) <= 2)    return (random.nextInt(5) + 5);
                    return random.nextInt(5);
                }
                return 0;
            case 1:
                if(isPlus){
                    if(random.nextInt(5) <= 2)    return (random.nextInt(4) + 5);
                    return random.nextInt(4);
                }
                return -(random.nextInt(2));
            case 2:
                if(isPlus){
                    if(random.nextInt(5) <= 2)    return (random.nextInt(4));
                    return random.nextInt(3) + 5;
                }
                return -(random.nextInt(3));
            case 3:
                if(isPlus){
                    if(random.nextInt(5) <= 2)    return random.nextInt(4);
                    return random.nextInt(2) + 5;
                }
                return -(random.nextInt(4));
            case 4:
                if(isPlus){
                    if(random.nextInt(5) <= 2)    return random.nextInt(4);
                    return random.nextInt(2)*5;
                }
                return -(random.nextInt(5));
            case 5:
                if(isPlus)  return random.nextInt(5);
                else{
                    if(random.nextInt(5) <= 2)    return -3;
                    return -(random.nextInt(4));
                }
            case 6:
                if(isPlus)  return random.nextInt(4);
                else{
                    if(random.nextInt(5) <= 2)    return -3;
                    return -random.nextInt(4);
                }
            case 7:
                if(isPlus)  return random.nextInt(3);
                else{
                    if(random.nextInt(5) <= 2)    return -3;
                    else{
                        if(random.nextBoolean())  return -(random.nextInt(3) + 5);
                        return -(random.nextInt(4));
                    }
                }
            case 8:
                if(isPlus)  return random.nextInt(2);
                else{
                    if(random.nextBoolean())  return -(random.nextInt(3) + 5);
                    return -(random.nextInt(4));
                }
            case 9:
                if(isPlus)  return 0;
                return -(random.nextInt(10));
        }
        return 0;
    }
}
