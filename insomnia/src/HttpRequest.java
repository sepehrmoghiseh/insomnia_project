import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http request class for making a request!
 */
public class HttpRequest extends SwingWorker<String, Object> {
    String commend;

    /**
     * constructor of the class
     */
    public HttpRequest(String commend) {
        this.commend = commend;
    }

    /**
     * take the commend an separate the headers,data,url.
     *
     * @param commend
     * @throws IOException
     */
    public static void http(String commend) throws IOException {
        String urlString = checkUrl(commend);
        if (commend.contains("-h") || commend.contains("--help")) {
            File file = new File("save.txt");
            System.out.println("-i : showing the headers\n-M --method : choose a requestMethod type\n-H --headers : set headers" +
                    "\n-h --help : help\n-O --output : save the response body in a (specific if a name has given) file" +
                    "\n-s --save : save the commend in the " + file.getAbsolutePath() + "\n-d --data : set the params");
        }
        if (urlString != null)
            if (urlString.contains("http://") == false) {
                urlString = "http://" + urlString;
            }
        try {
            if (urlString.charAt(urlString.length() - 1) != '/' || urlString.charAt(urlString.length() - 1) != '\\') {
                urlString = urlString + "/";
            }
        } catch (Exception e) {
            System.err.print("");
        }
        if (urlString == null)
            System.err.print("");
        String headers = headers(commend);
        String data = param(commend);
        String requestType = requestType(commend);
        if (commend.contains("-s") || commend.contains("--save")) {
            saveCommend(commend);
        }
        if (urlString != null) {
            if (requestType == null) {
                connection(urlString, data, headers, "GET", commend);

            } else
                connection(urlString, data, headers, requestType, commend);
        }
    }

    /**
     * make the connection with the headers ,params & url.
     *
     * @param urlS
     * @param data
     * @param headers
     * @param requestType
     * @param commend
     * @throws IOException
     */
    public static void connection(String urlS, String data, String headers, String requestType, String commend) throws IOException {
        URL url = null;
        String response = null;
        int flag = 0;
        HttpURLConnection con = null;

        try {
            url = new URL(urlS);
        } catch (MalformedURLException e) {

        }
        try {
            con = (HttpURLConnection) url.openConnection();
            if (commend.contains("-f")) {
                HttpURLConnection.setFollowRedirects(true);
                con.setInstanceFollowRedirects(true);
            } else {
                HttpURLConnection.setFollowRedirects(false);
            }

        } catch (IOException e) {

        }
        try {
            con.setRequestMethod(requestType);
        } catch (ProtocolException e) {
            flag = 1;
            con.setRequestMethod("GET");
        }

        while (headers != null) {
            String string = null;
            String string2 = null;
            string = headers.substring(0, headers.indexOf(':'));
            headers = headers.substring(headers.indexOf(':') + 1);
            if (headers.contains(";")) {
                string2 = headers.substring(0, headers.indexOf(';'));
                con.setRequestProperty(string, string2);
            } else {
                string2 = headers.substring(0, headers.length());
                con.setRequestProperty(string, string2);
                break;
            }
            headers = headers.substring(headers.indexOf(';') + 1);

        }
        if (data != null) {
            if (flag == 0) {
                if (requestType.equals("POST")) {
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();
                }
                if (requestType.equals("PUT")) {
                    con.setRequestMethod("PUT");
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();
                }
                if (requestType.equals("PATCH")) {
                    con.setRequestMethod("PATCH");
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();
                }
                if (requestType.equals("DELETE")) {
                    con.setRequestMethod("DELETE");
                    con.setDoOutput(true);
                    OutputStream os = con.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();
                }
            }

        }
        try {
            getFullResponse(con, commend, urlS);

        } catch (IOException e) {
            Gui.setStatus("error" + " " + con.getResponseCode() + " " + con.getResponseMessage());
            Gui.setTime("-");
            Panel3.setRawText("Error!");
        }


    }

    /**
     * a class for save the commends in a text file
     *
     * @param commend
     * @throws IOException
     */
    public static void saveCommend(String commend) throws IOException {
        String save = null;

        String tempWord = "-s";
        String tempWord2 = "--save";
        save = commend.replaceAll(tempWord2, "");
        save = save.replaceAll(tempWord, "");

        FileWriter fileWriter = new FileWriter("saves.txt", true);
        PrintWriter out = new PrintWriter(fileWriter);
        out.println(save);
        out.close();
        out.flush();
    }

    /**
     * * a class for save the responses in a binary file or text file that depends on the content type.
     *
     * @param response
     * @param commend
     * @param urlS
     */
    public static void saveResponse(String response, String commend, String urlS) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        LocalDateTime now = LocalDateTime.now();
        String name = "output_" + dtf.format(now);
        String nameChoose = checkName(commend);
        if (nameChoose != null && nameChoose.equals("--output") == false && nameChoose.equals("-O") == false && nameChoose.equals("-i") == false && nameChoose.equals("-M") == false && nameChoose.equals("--method") == false && nameChoose.equals("-H") == false && nameChoose.equals("--headers") == false && nameChoose.equals("-d") == false && nameChoose.equals("--data") == false && nameChoose.equals("-s") == false && nameChoose.equals("--save") == false) {
            name = nameChoose;
        }
        URL url = null;
        try {
            url = new URL(urlS);
        } catch (MalformedURLException e) {
            Gui.setStatus("error");
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            Gui.setStatus("error");
        }
        String content = con.getContentType();
        long contentLength = con.getContentLengthLong();

        try (InputStream in = con.getInputStream(); FileOutputStream out = new FileOutputStream(name + ".png")) {
            int totalRead = 0;
            byte[] buffer = new byte[1000000];
            while (totalRead < contentLength) {
                int read = in.read(buffer);
                if (read == -1)
                    break;
                out.write(buffer, 0, read);
                totalRead += read;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (content.contains("text/")) {
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(name + ".html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrintWriter out = new PrintWriter(fileWriter);
            out.println(response);
            out.close();

        }
    }

    /**
     * get the full response that contains the headers and the raw file and a visual preview of the site
     *
     * @param con
     * @param commend
     * @param urls
     * @throws IOException
     */
    public static void getFullResponse(HttpURLConnection con, String commend, String urls) throws IOException {
        StringBuilder fullResponseBuilder2 = new StringBuilder();
        long millis = System.currentTimeMillis();
        Gui.setStatus(con.getResponseCode() + " " + con.getResponseMessage());
        guiHeaders(con);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            fullResponseBuilder2.append(inputLine);
            fullResponseBuilder2.append("\n");
        }
        in.close();
        con.disconnect();
        if (commend.contains("-O") || commend.contains("--output"))
            saveResponse(fullResponseBuilder2.toString(), commend, urls);

        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        URL url = new URL(urls);
        try {
            jEditorPane.setPage(url);
            jEditorPane.setPreferredSize(new Dimension(400, 1200));
        } catch (IOException e) {
            jEditorPane.setContentType("text/html");
            jEditorPane.setText("<html>Page not found.</html>");
        }
        Panel3.preview(jEditorPane);
        long millis2 = System.currentTimeMillis();
        Long millis3 = millis2 - millis;
        Gui.setTime(millis3.toString() + "mS");
        Panel3.setRawText(fullResponseBuilder2.toString());

    }

    /**
     * found out whats the request type
     *
     * @param commend
     * @return
     */
    public static String requestType(String commend) {
        if (commend.contains("-M ")) {
            if (commend.contains("GET"))
                return "GET";
            if (commend.contains("POST"))
                return "POST";
            if (commend.contains("DELETE"))
                return "DELETE";
            if (commend.contains("PUT"))
                return "PUT";
            if (commend.contains("PATCH"))
                return "PATCH";
        }
        if (commend.contains("--method")) {
            if (commend.contains("GET"))
                return "GET";
            if (commend.contains("POST"))
                return "POST";
            if (commend.contains("DELETE"))
                return "DELETE";
            if (commend.contains("PUT"))
                return "PUT";
            if (commend.contains("PATCH"))
                return "PATCH";
        }
        return "GET";
    }

    /**
     * make the headers for gui part
     *
     * @param con
     */
    public static void guiHeaders(HttpURLConnection con) {
        StringBuilder fullResponseBuilder = new StringBuilder();
        con.getHeaderFields().entrySet().stream()
                .filter(entry -> entry.getKey() != null)
                .forEach(entry -> {
                    fullResponseBuilder.append(entry.getKey()).append(": ");
                    List headerValues = entry.getValue();
                    Iterator it = headerValues.iterator();
                    if (it.hasNext()) {
                        fullResponseBuilder.append(it.next());
                        while (it.hasNext()) {
                            fullResponseBuilder.append(", ").append(it.next());
                        }
                    }
                    fullResponseBuilder.append("\n");
                });
        Panel3.addInHeaders(fullResponseBuilder.toString());
    }

    /**
     * separate the params from the commend
     *
     * @param commend
     * @return
     */
    public static String param(String commend) {
        int number2 = 0;
        int number = 0;
        if (commend.contains("-d ")) {
            number = commend.indexOf("-d") + 3;
            for (int i = commend.indexOf("-d") + 4; i < commend.length(); i++) {
                if (commend.charAt(i) == '"') {
                    number2 = i;
                    break;
                }
            }
        } else if (commend.contains("--data")) {
            number = commend.indexOf("--data") + 7;

            for (int i = commend.indexOf("--data") + 8; i < commend.length(); i++) {
                if (commend.charAt(i) == '"') {
                    number2 = i;
                    break;
                }
            }
        }
        if (number2 != 0)
            return commend.substring(number + 1, number2);
        return null;
    }

    /**
     * separate the headers from the commend.
     *
     * @param commend
     * @return
     */
    public static String headers(String commend) {
        int number2 = 0;
        int number = 0;
        if (commend.contains("-H")) {
            number = commend.indexOf("-H") + 3;
            for (int i = commend.indexOf("-H") + 4; i < commend.length(); i++) {
                if (commend.charAt(i) == '"') {
                    number2 = i;
                    break;
                }
            }
        } else if (commend.contains("--headers")) {
            number = commend.indexOf("--headers") + 10;

            for (int i = commend.indexOf("--headers") + 11; i < commend.length(); i++) {
                if (commend.charAt(i) == '"') {
                    number2 = i;
                    break;
                }
            }
        }
        if (number2 != 0)
            return commend.substring(number + 1, number2);
        return null;
    }

    /**
     * check the name for the output
     *
     * @param check
     * @return
     */
    public static String checkName(String check) {
        String string = null;
        Pattern p = Pattern.compile("(([a-zA-Z0-9-]){1,})");//. represents single character
        Matcher matcher = p.matcher(check);
        while (matcher.find()) {
            string = check.substring(matcher.start(), matcher.end());
        }
        return string;
    }

    /**
     * separate the url from the commend
     *
     * @param check
     * @return
     */
    public static String checkUrl(String check) {
        String string = null;
        String http = "((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";
        Pattern pattern = Pattern.compile(http);
        Matcher matcher = pattern.matcher(check);
        while (matcher.find()) {
            string = check.substring(matcher.start(), matcher.end());
        }
        return string;
    }

    /**
     * show the list of saved commends in the save file
     *
     * @throws IOException
     */
    public static void showList() throws IOException {
        File saveFile = new File("saves.txt");
        if (saveFile.exists() == false) {
            System.out.println("");
        } else {
            int i = 1;
            String line;
            FileReader fileReader = new FileReader(saveFile);
            BufferedReader br = new BufferedReader(fileReader);
            while ((line = br.readLine()) != null) {
                System.out.println(i + ". " + formattedLine(line));
                i++;
            }
        }
    }

    /**
     * format the lines for list
     *
     * @param str
     * @return
     */
    public static String formattedLine(String str) {
        String line;
        String url = checkUrl(str);
        String headers = headers(str);
        String params = param(str);
        if (params == null) {
            params = " ";
        }
        if (headers == null) {
            headers = " ";
        }
        if (url == null) {
            url = " ";
        }
        String requestMethod = requestType(str);
        return line = "URL: " + url + " | " + "Method: " + requestMethod + " | " + "Headers: " + headers + " | " + "Params: " + params;
    }

    /**
     * read line from list
     *
     * @param i
     */
    public static void readLine(int i) throws IOException {
        File saveFile = new File("saves.txt");
        String text = "";
        if (saveFile.exists() == false) {
            System.out.println("");
        } else {
            try {
                FileReader readfile = new FileReader(saveFile);
                BufferedReader readbuffer = new BufferedReader(readfile);
                int lineNumber = 1;
                while ((text = readbuffer.readLine()) != null) {
                    if (lineNumber == i) {
                        http(text);
                        break;
                    } else
                        lineNumber++;

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * the fire commend for the console part
     *
     * @param str
     * @throws IOException
     */
    public static void fire(String str) throws IOException {
        str = str.substring(str.indexOf("e") + 2);
        String[] newStr = str.split(" ");
        for (int i = 0; i < newStr.length; i++) {
            int num = Integer.parseInt(newStr[i]);
            readLine(num);

        }

    }

    /**
     * java swing worker to stop from freezing.
     */
    @Override
    protected String doInBackground() throws Exception {
        http(commend);
        return null;
    }
}
