package com.cheatbreaker.client.util.render.wordwrap;

import java.util.Arrays;

final class StringWrapHelper implements CharSequence {

    private char[] chars;
    private int length;

    StringWrapHelper(String s) {
        this(s.toCharArray(), s.length());
    }

    StringWrapHelper() {
        this(new char[16], 0);
    }

    private StringWrapHelper(char[] chars, int length) {
        this.chars = chars;
        this.length = length;
    }

    char[] internalArray() {
        return chars;
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public char charAt(int index) {
        return chars[index];
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        char[] chars2 = new char[end - start];
        System.arraycopy(chars, start, chars2, 0, end - start);
        return new StringWrapHelper(chars2, chars2.length);
    }

    public void append(StringWrapHelper s) {
        int len = s.length();
        checkSize(len);
        System.arraycopy(s.chars, 0, chars, length, len);
        length += len;
    }

    private void checkSize(int len) {
        if (length + len > chars.length) {
            chars = Arrays.copyOf(chars, newSize(len));
        }
    }

    private int newSize(int len) {
        int newSize = chars.length * 2;
        if (newSize < length + len) {
            newSize = length + len;
        }
        return newSize;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void append(char ch) {
        checkSize(1);
        chars[length] = ch;
        length++;
    }

    public void delete(int start, int end) {
        System.arraycopy(chars, end, chars, start, length - end);
        length -= end - start;
    }

    public String substring(int start, int end) {
        return new String(chars, start, end - start);
    }

    @Override
    public String toString() {
        return new String(chars, 0, length);
    }

    /**
     * Trims right space from this and returns {@code this}.
     *
     * @return this
     */
    StringWrapHelper rightTrim() {
        int i = length();
        while (i > 0) {
            if (!Character.isWhitespace(charAt(i - 1))) {
                break;
            }
            i--;
        }
        length = i;
        return this;
    }
}