import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.Writer;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class JoinerTest {

    /******************************************************************************
     *  참고 : Getting Started with Google Guava
     *
     *  Joiner 클래스는 on 메서드를 호출해서 생성할 수 있다.
     *  불변 객체로 static final로 사용되고, thread-safe하다.
     *
     *  ex) Joiner.on("|").join(values);
     *  ex) Joiner.on("|").skipNulls().join(values);
     *  ex) Joiner.on("|").useForNull("missing").join(values);
     ******************************************************************************/

    @Test
    public void existingCode() {
        String[] stringList = new String[]{"foo", "bar", "baz"};
        String delimiter = "|";

        StringBuilder builder = new StringBuilder();
        for (String s : stringList) {
            if (s != null) {
                builder.append(s).append(delimiter);
            }
        }
        builder.setLength(builder.length() - delimiter.length());
        assertThat(builder.toString(), is("foo|bar|baz"));
    }

    @Test
    public void testJoinStrings() {
        String[] values = new String[]{"foo", "bar", "baz"};

        String returned = Joiner.on("|").join(values);
        assertThat(returned, is("foo|bar|baz"));
    }

    @Test
    public void testJoinStringBuilder() {
        String[] values = new String[]{"foo", "bar", "baz"};
        StringBuilder builder = new StringBuilder();
        StringBuilder returned = Joiner.on("|").appendTo(builder, values);
        assertThat(returned, is(builder));
        assertThat(returned.toString(), is("foo|bar|baz"));
    }

    @Test
    public void testJoinStringsSkipNull() {
        String[] values = new String[]{"foo", null, "bar"};
        String returned = Joiner.on("|").skipNulls().join(values);
        assertThat(returned, is("foo|bar"));
    }

    @Test
    public void testJoinStringsUseForNull() {
        String[] values = new String[]{"foo", null, "bar"};
        String returned = Joiner.on("|").useForNull("missing").join(values);
        assertThat(returned, is("foo|missing|bar"));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void testJoinerUnsupportedOperation() {
        Joiner stringJoiner = Joiner.on("|").skipNulls();

        //the useForNull() method returns a new instance of the Joiner!
        stringJoiner.useForNull("missing");

        // Once created, a Joiner class is immutable, and therefore thread-safe, and can be used as a static final variable.
        stringJoiner.join("foo", "bar", null);

        fail("Should not get here");
    }

    @Test(expected = NullPointerException.class)
    public void testJoinStringsNoNullHandler() {
        String[] values = new String[]{"foo", null, "bar"};
        Joiner.on("|").join(values);
        fail("Should not get here");
    }

    @Test
    public void testJoinFileWriter() throws Exception {
        File tempFile = new File("testTempFile.txt");
        tempFile.deleteOnExit();
        CharSink charSink = Files.asCharSink(tempFile, Charsets.UTF_8);
        Writer writer = charSink.openStream();
        String[] values = new String[]{"foo", "bar", "baz"};
        Joiner.on("|").appendTo(writer, values);
        writer.flush();
        writer.close();
        String fromFileString = Files.toString(tempFile, Charsets.UTF_8);
        assertThat(fromFileString, is("foo|bar|baz"));
    }

    @Test
    public void testMapJoiner() {
        //Using LinkedHashMap so that the original order is preserved
        Map<String, String> testMap = Maps.newLinkedHashMap();
        testMap.put("Washington D.C", "Redskins");
        testMap.put("New York City", "Giants");
        testMap.put("Philadelphia", "Eagles");
        testMap.put("Dallas", "Cowboys");

        String expectedString = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
        String returnedString = Joiner.on("#").withKeyValueSeparator("=").join(testMap);
        assertThat(returnedString, is(expectedString));
    }
}
