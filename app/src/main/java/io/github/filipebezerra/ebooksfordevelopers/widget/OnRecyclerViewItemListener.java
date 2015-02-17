package io.github.filipebezerra.ebooksfordevelopers.widget;

import android.view.*;
import io.github.filipebezerra.ebooksfordevelopers.model.*;

public interface OnRecyclerViewItemListener<RecyclerModel> {
	
	void onItemClick(View view, RecyclerModel model);
	
}
