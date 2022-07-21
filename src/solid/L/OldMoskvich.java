package solid.L;

class Taxi{
    void drive(){};
}

public class OldMoskvich extends Taxi{
    void drive(){
        System.out.println("Ну и колымага этот москвич");
    }
}

class LadaGranta extends OldMoskvich {
    void drive(){
        System.out.println("Лада не огонь конечно, но ехать можно");
    }
}

class KIARio extends Taxi{
    void drive(){
        System.out.println("KIA супер, езда в удовольствие ");
    }
}


class IdentifyException extends Exception{
}

class LandRover extends Taxi{

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

class TaxiDrtiver{
    public void setAuto(Taxi taxi_for_today){
        taxi_for_today.drive();
    }
}

class TaxiCompany{

    public static void main(String[] args) {
        OldMoskvich oldMoskvich = new OldMoskvich();
        LadaGranta ladaGranta = new LadaGranta();
        KIARio kiaRio = new KIARio();
        LandRover landRover = new LandRover();

        TaxiDrtiver drtiver = new TaxiDrtiver();
        drtiver.setAuto(oldMoskvich);
        drtiver.setAuto(ladaGranta);
        drtiver.setAuto(kiaRio);
        drtiver.setAuto(landRover);
    }
}



