package solid.O;

interface Logger{
   void log();
   void appenfFile();
}

class FileLogger implements Logger{
    @Override
    public void log() { }
    @Override
    public void appenfFile() { }
}

class DBLogger implements Logger{
    @Override
    public void log() { }
    @Override
    public void appenfFile() { }
}

public class SuperPuperImportantClass {

    private Logger logger;

    public SuperPuperImportantClass(Logger logger) {
        this.logger = logger;
    }

}






