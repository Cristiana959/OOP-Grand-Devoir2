package sample;

import java.io.*;

public class HTML_gen {
    private File htmlFile;
    private StringBuffer  stringBuilder;

    public void startDoc(StringBuffer sb)
    {
        String start = "<html><body>";
        sb.append(start);
    }

    public void finishDoc(StringBuffer sb)
    {
        String finish = "</body></html>";
        sb.append(finish);
    }


    public void appendTag(StringBuffer sb, String tag, String contents) {
        sb.append('<').append(tag).append('>');
        sb.append(contents);
        sb.append("</").append(tag).append('>');
    }

    public HTML_gen(File htmlFile, StringBuffer stringBuilder) {
        this.htmlFile = htmlFile;
        this.stringBuilder = stringBuilder;
    }

    public void createHTMLFile(StringBuffer  sb, File file) throws IOException {
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(String.valueOf(sb));
        bufferedWriter.close();
    }


}
