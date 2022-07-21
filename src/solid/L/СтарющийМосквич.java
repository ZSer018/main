package SOLID.L;

class Такси{
    void drive(){};
}

public class СтарющийМосквич extends Такси{
    void drive(){
        System.out.println("Ну и колымага этот москвич");
    }
}

class ЛадаГранта extends СтарющийМосквич {
    void drive(){
        System.out.println("Лада не огонь конечно, но ехать можно");
    }
}

class KIARio extends Такси {
    void drive(){
        System.out.println("KIA супер, езда в удовольствие ");
    }
}


class IdentifyException extends Exception{

}

class LandRover extends Такси{

    static void identify() throws IdentifyException{
        throw new IdentifyException();
    }

    void drive() {
        try {
            System.out.println("Пожалуйста, приложите палец для идентификации");
            identify();
        } catch (IdentifyException e) {
            System.out.println("Идентификация не удалась. В старте двигателя отказано");
            throw new RuntimeException("Ошибка идентификации");
        }

        System.out.println("Ого, дали ровер сегодня. Вот это повезло");
    };
}







class Таксист{
    private Такси авто;

    public void setАвто(Такси такси_на_сегодня){
        авто = такси_на_сегодня;

        авто.drive();
    }
}

class Таксопарк{

    public static void main(String[] args) {
        СтарющийМосквич москвич = new СтарющийМосквич();
        ЛадаГранта лада = new ЛадаГранта();
        KIARio kiaRio = new KIARio();
        LandRover landRover = new LandRover();

        Таксист таксист = new Таксист();
        таксист.setАвто(москвич);
        таксист.setАвто(лада);
        таксист.setАвто(kiaRio);
        таксист.setАвто(landRover);
    }
}



