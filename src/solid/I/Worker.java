package solid.I;

public interface Worker {
    void work();
}

interface HumanWorker extends Worker{
    void rest();
    void receivePayment();
}

class Welder implements HumanWorker{
    @Override
    public void work() { }

    @Override
    public void rest() { }

    @Override
    public void receivePayment() {  }
}

class Locksmith implements HumanWorker{
    @Override
    public void work() { }

    @Override
    public void rest() { }

    @Override
    public void receivePayment() { }
}

class MechWelder implements Worker{
    @Override
    public void work() { }
}

