package com.nadun.blog.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtil {
    private Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String toSlug(String input) {
        SlugUtil slugUtil = new SlugUtil();

        if (input == null)
            return "";

        String slug = input.trim().toLowerCase(); // Trim and convert to lowercase

        // Normalize the string
        slug = Normalizer.normalize(slug, Normalizer.Form.NFD);

        // Remove diacritics
        slug = slug.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Replace whitespace with hyphens
        slug = slugUtil.WHITESPACE.matcher(slug).replaceAll("-");

        // Remove non-latin characters
        slug = slugUtil.NON_LATIN.matcher(slug).replaceAll("");

        // Remove multiple consecutive hyphens
        slug = slug.replaceAll("-+", "-");

        return slug;
    }
}
