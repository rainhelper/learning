import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SplitterTest {

    /******************************************************************************
     *  참고 : Getting Started with Google Guava
     *
     *  Splitter 클래스는 on 메서드를 호출해서 생성할 수 있다.
     *  불변 객체로 static final로 사용되고, thread-safe하다.
     *
     *  ex) Splitter.onPattern(pattern) = Splitter.on(Pattern.compile(pattern))
     *  ex) Splitter.on("#").trimResults(CharMatcher.JAVA_DIGIT).split(string);
     *  ex) Splitter.on("#").omitEmptyStrings().split(string);
     *******************************************************************************/

    @Test
    public void testSplitter() {
        String startSring = "Washington D.C=Redskins#New York City=Giants#Philadelphia=Eagles#Dallas=Cowboys";
        Map<String, String> testMap = Maps.newLinkedHashMap();
        testMap.put("Washington D.C", "Redskins");
        testMap.put("New York City", "Giants");
        testMap.put("Philadelphia", "Eagles");
        testMap.put("Dallas", "Cowboys");
        Splitter.MapSplitter mapSplitter = Splitter.on("#").withKeyValueSeparator("=");
        Map<String, String> splitMap = mapSplitter.split(startSring);
        assertThat(testMap, is(splitMap));
    }

    @Test
    public void testSplitPattern() {
        String pattern = "\\d+";
        String text = "foo123bar45678baz";
        String[] expected = new String[]{"foo", "bar", "baz"};
        Iterable<String> values = Splitter.on(Pattern.compile(pattern)).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value, is(expected[index++]));
        }
    }

    @Test
    public void testSplitStringPattern() {
        String pattern = "\\d+";
        String text = "foo123bar45678baz";
        String[] expected = new String[]{"foo", "bar", "baz"};
        Iterable<String> values = Splitter.onPattern(pattern).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value, is(expected[index++]));
        }
    }

    @Test
    public void testSplit() {
        String delimiter = "&";
        String text = "foo&bar&baz";
        String[] expected = new String[]{"foo", "bar", "baz"};
        Iterable<String> values = Splitter.on(delimiter).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value, is(expected[index++]));
        }
    }

    @Test
    public void testSplitTrimResults() {
        String delimiter = "&";
        String text = "foo   &  bar&   baz  ";
        String[] expected = new String[]{"foo", "bar", "baz"};
        Iterable<String> values = Splitter.on(delimiter).trimResults().split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value, is(expected[index++]));
        }
    }

    @Test
    public void testSplitTrimResultsII() {
        String delimiter = "&";
        String text = "1foo&bar2&2baz3";
        String[] expected = new String[]{"foo", "bar", "baz"};
        Iterable<String> values = Splitter.on(delimiter).trimResults(CharMatcher.JAVA_DIGIT).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value, is(expected[index++]));
        }
    }

    @Test
    public void testSplitOnCharacter() {
        char delimiter = '|';
        String text = "foo|bar|baz";
        String[] expected = new String[]{"foo", "bar", "baz"};
        Iterable<String> values = Splitter.on(delimiter).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value, is(expected[index++]));
        }
    }

    @Test
    public void testSplitOnCharacterKeepMissing() {
        char delimiter = '|';
        String text = "foo|bar|||baz";
        String[] expected = new String[]{"foo", "bar", "", "", "baz"};
        Iterable<String> values = Splitter.on(delimiter).split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value, is(expected[index++]));
        }
    }

    @Test
    public void testSplitOnCharacterOmitMissing() {
        char delimiter = '|';
        String text = "foo|bar|||baz";
        String[] expected = new String[]{"foo", "bar", "baz"};
        Iterable<String> values = Splitter.on(delimiter).omitEmptyStrings().split(text);
        int index = 0;
        for (String value : values) {
            assertThat(value, is(expected[index++]));
        }
    }
}
