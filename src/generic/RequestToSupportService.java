package GenericLec;

import java.util.zip.ZipEntry;


public class RequestToSupportService<
        D extends TextDocument,
        A extends Archive & LimitedSize & ZIP,
        M extends Email> {

    private D document;
    private A archive;
    private M eMail;

    // getters, setters...

    public static void main(String[] args) {
        RequestToSupportService<TextDocument, ZipAttachmentArchive, Email> request = new RequestToSupportService<>();
    }

}







