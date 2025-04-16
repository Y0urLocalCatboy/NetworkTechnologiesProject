package com.example.firstproject.service.valueObjects;

public class Description {
    private final String value;

    public Description(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    private static String formatDescription(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }

        String trimmed = value.trim();

        StringBuilder formatted = new StringBuilder();
        formatted.append(Character.toUpperCase(trimmed.charAt(0)));
        if (trimmed.length() > 1) {
            formatted.append(trimmed.substring(1));
        }

        String[] abbreviations = {"mr.", "mrs.", "ms.", "dr.", "prof.", "e.g.", "i.e.", "etc."};

        for (int i = 0; i < formatted.length() - 1; i++) {
            if (formatted.charAt(i) == '.' &&
                    ((i > 0 && formatted.charAt(i-1) == '.') ||
                            (i < formatted.length() - 1 && formatted.charAt(i+1) == '.'))) {
                continue;
            }

            if (formatted.charAt(i) == '.') {
                boolean isAbbreviation = false;
                for (String abbr : abbreviations) {
                    if (i >= abbr.length() - 1) {
                        String fragment = formatted.substring(Math.max(0, i - abbr.length() + 1), i + 1).toLowerCase();
                        if (abbr.equals(fragment)) {
                            isAbbreviation = true;
                            break;
                        }
                    }
                }

                if (!isAbbreviation && i < formatted.length() - 1) {
                    if (formatted.charAt(i + 1) != ' ') {
                        formatted.insert(i + 1, ' ');
                        i++;
                    }
                    if (i + 1 < formatted.length() - 1) {
                        formatted.setCharAt(i + 2, Character.toUpperCase(formatted.charAt(i + 2)));
                    }
                }
            }
        }

        if (formatted.length() > 0 && formatted.charAt(formatted.length() - 1) != '.') {
            formatted.append('.');
        }

        return formatted.toString();
    }

    public static Description create(String value) {
        return new Description(formatDescription(value));    }
}
