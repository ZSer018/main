package GenericLec;


class SupportQueryForm<
        QT extends QueryText,
        P extends Person,
        A extends Archive>{

    private QT querryText;
    private P person;
    private A archive;

    public void setQuerryText(QT querryText) {
        this.querryText = querryText;
    }

    public void setPerson(P person) {
        this.person = person;
    }

    public void setArchive(A archive) {
        this.archive = archive;
    }
}

class QueryText{}
class Person{}
class PersonDataBase{
    static Person getPersonById(int Id){return new Person();}
}
class Text{}
class HardwareProblemQueryText extends QueryText{
    Text text;

    public HardwareProblemQueryText(Text text) {
        this.text = text;
    }
}



public class MyClass<T> {

    private T[] t;

    public MyClass(T[] t) {
        this.t = t;
    }









    public static void main(String[] args) {

        Integer[] a = new Integer[10];
        Number[] b = a;
        b[0] = 9.99;

        int personId= 0;
        Text textFromQueryPageForm = new Text();


        Person person = PersonDataBase.getPersonById(personId);
        SupportQueryForm<QueryText, Person, ZipArchive> supportQueryForm = new SupportQueryForm<>();

        supportQueryForm.setPerson( person );
        supportQueryForm.setQuerryText( new HardwareProblemQueryText( textFromQueryPageForm ) );


    }

}







