package solid.O;

interface Logger{
   void log();
   void appendFile();
}

class FileLogger implements Logger{
    @Override
    public void log() { }
    @Override
    public void appendFile() { }
}

class DBLogger implements Logger{
    @Override
    public void log() { }
    @Override
    public void appendFile() { }
}

public class SuperPuperImportantClass {

    private Logger logger;

    public SuperPuperImportantClass(Logger logger) {
        this.logger = logger;
    }

}






