/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.view.AbstractVisibility;

import android.view.View;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FooterVisibility extends AbstractVisibility {
    private ListView listView;
    private View footerView;

    public FooterVisibility(ListView listView, View footerView) {
	this.listView = listView;
	this.footerView = footerView;
    }

    @Override
    public void makeGone() {
	if (listView.getAdapter() == null) {
	    return;
	}

	if (listView.getFooterViewsCount() > 0) {
	    listView.removeFooterView(footerView);
	}
    }

    @Override
    public void makeVisible() {
	addFooterViewIfNotExist();
	footerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void makeInvisible() {
	addFooterViewIfNotExist();
	footerView.setVisibility(View.INVISIBLE);
    }

    private void addFooterViewIfNotExist() {
	if (listView.getFooterViewsCount() == 0) {
	    listView.addFooterView(footerView);
	}
    }
}
