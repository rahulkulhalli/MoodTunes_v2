package com.moodtunes.moodtunes_v2.interfaces;

import android.view.View;

/**
 * An interface to provide consistent implementation of the Toolbar across all
 * fragments/activities.
 *
 * <hr>
 *
 * It is to be noted that the developer is expected to create a Toolbar member
 * variable, and set it's content view. These methods simply act as containers
 * for a neat looking implementation.
 */
public interface ToolbarInterface {

    /**
     * A method to initialize the Toolbar.
     *
     * @param toolbarTitle Title of the toolbar.
     */
    void initializeToolbar(String toolbarTitle);

    /**
     * A method to set the Toolbar title.
     *
     * @param title Title of the Toolbar.
     */
    void setToolbarTitle(String title);

    /**
     * A method to set the custom drop-down listener.
     *
     */
    void setDropdownListener();
}
