package io.github.filipebezerra.ebooksfordevelopers.provider;

import android.content.*;

public class SuggestionsProvider extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY = SuggestionsProvider.class.getName();
    public static final int MODE = DATABASE_MODE_QUERIES;

    public SuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
	
}
