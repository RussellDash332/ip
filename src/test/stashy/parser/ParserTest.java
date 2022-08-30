package test.stashy.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    @Test
    public void todoRegexTest() {
        String todo = "[T][X] read book";
        final Matcher matcher = Parser.TASK_DATA_FORMAT.matcher(todo);
        if (!matcher.matches()) {
            fail();
        }
        assertEquals("read book", matcher.group("description"));
        assertEquals("X", matcher.group("isDone"));
        assertEquals("T", matcher.group("category"));
        assertEquals("", matcher.group("at"));
    }

    @Test
    public void eventRegexTest() {
        String event = "[E][ ] read book (at: 10-01-2020 18:30)";
        final Matcher matcher = Parser.TASK_DATA_FORMAT.matcher(event);
        if (!matcher.matches()) {
            fail();
        }
        assertEquals("read book", matcher.group("description").strip());
        assertEquals(" ", matcher.group("isDone"));
        assertEquals("E", matcher.group("category"));
        assertEquals("10-01-2020 18:30", matcher.group("at").replaceAll("at: ", "").strip());
        assertEquals("", matcher.group("by").replaceAll("by: ", "").strip());
    }

    @Test
    public void deadlineRegexTest() {
        String deadline = "[D][ ] read book (by: 10-01-2020 18:30)";
        final Matcher matcher = Parser.TASK_DATA_FORMAT.matcher(deadline);
        if (!matcher.matches()) {
            fail();
        }
        assertEquals("read book", matcher.group("description").strip());
        assertEquals(" ", matcher.group("isDone"));
        assertEquals("D", matcher.group("category"));
        assertEquals("10-01-2020 18:30", matcher.group("by").replaceAll("by: ", "").strip());
        assertEquals("", matcher.group("at").replaceAll("at: ", "").strip());
    }

    @Test
    public void testDateTime() {
        String[] dateTimeStrings = {
                "Dec 12 2018 1030", "Dec 12 2018 10:31",
                "25/12/2016 0027", "25/12/2015 00:27",
                "2020/01/01 1201", "2020/01/01 12:01",
                "2022/07/14'T'2358", "2022/07/14'T'23:59",
                "2021-08-11 1111", "2021-08-10 11:11",
                "19 Apr 2013 1725", "19 Apr 2031 17:25",
                "Sep 30, 1965 1801", "Sep 30, 1965 18:00"};

        for (String dateTimeString : dateTimeStrings) {
            for (String dateTimeFormat : Parser.ACCEPTABLE_DATETIME_FORMATS) {
                try {
                    LocalDateTime by = LocalDateTime.parse(dateTimeString,
                            DateTimeFormatter.ofPattern(dateTimeFormat)
                                    .withResolverStyle(ResolverStyle.SMART));
                    continue;
                } catch (Exception e) {
                }
                fail();
            }
        }
    }
}