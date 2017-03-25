package com.moodtunes.moodtunes_v2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.moodtunes.moodtunes_v2.R;
import com.moodtunes.moodtunes_v2.activities.HomeScreenActivity;
import com.moodtunes.moodtunes_v2.interfaces.ToolbarInterface;

public class FaqFragment extends Fragment {

    private WebView wbFaq;

    private static final String FAQ_URI = "file:///android_asset/FAQ.html";
    private static final String FAQ_MIME_TYPE = "text/html";
    private static final String FAQ_ENCODING = "utf-8";

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View faqView = inflater.inflate(R.layout.fragment_faq, container, false);
        wbFaq = (WebView) faqView.findViewById(R.id.faq_view);
        return faqView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialize();
    }

    private void initialize() {
        wbFaq.loadUrl(FAQ_URI);
        ((HomeScreenActivity) getActivity()).initializeToolbar("FAQ");
    }
}
