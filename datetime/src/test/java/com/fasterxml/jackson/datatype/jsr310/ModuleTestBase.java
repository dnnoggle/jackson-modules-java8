package com.fasterxml.jackson.datatype.jsr310;

import java.util.Arrays;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.MapperBuilder;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class ModuleTestBase
{
    // 14-Mar-2016, tatu: Serialization of trailing zeroes may change [datatype-jsr310#67]
    //   Note, tho, that "0.0" itself is special case; need to avoid scientific notation:
    final static String NO_NANOSECS_SER = "0.0";
    final static String NO_NANOSECS_SUFFIX = ".000000000";

    protected static ObjectMapper newMapper() {
        return newMapperBuilder().build();
    }

    protected static MapperBuilder<?,?> newMapperBuilder() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule());
    }

    protected static MapperBuilder<?,?> newMapperBuilder(TimeZone tz) {
        return JsonMapper.builder()
                .defaultTimeZone(tz)
                .addModule(new JavaTimeModule());
    }

    protected static ObjectMapper newMapper(TimeZone tz) {
        return newMapperBuilder(tz).build();
    }

    protected String quote(String value) {
        return "\"" + value + "\"";
    }

    protected String aposToQuotes(String json) {
        return json.replace("'", "\"");
    }

    protected void verifyException(Throwable e, String... matches)
    {
        String msg = e.getMessage();
        String lmsg = (msg == null) ? "" : msg.toLowerCase();
        for (String match : matches) {
            String lmatch = match.toLowerCase();
            if (lmsg.indexOf(lmatch) >= 0) {
                return;
            }
        }
        throw new Error("Expected an exception with one of substrings ("+Arrays.asList(matches)+"): got one with message \""+msg+"\"");
    }
}
