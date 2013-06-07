/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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
package org.robobinding.binder;

import org.robobinding.BinderFactory;
import org.robobinding.BinderImplementor;
import org.robobinding.InternalViewBinder;
import org.robobinding.ItemBinder;
import org.robobinding.NonBindingViewInflater;

import android.content.Context;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class BinderFactoryImpl implements BinderFactory {
    private final BinderImplementor binderImplementor;
    private final Context context;
    
    public BinderFactoryImpl(BinderImplementor binderImplementor, Context context) {
	this.binderImplementor = binderImplementor;
	this.context = context;
    }

    @Override
    public ItemBinder createItemBinder() {
	return new ItemBinder(binderImplementor);
    }

    @Override
    public InternalViewBinder createInternalViewBinder() {
	return new InternalViewBinder(binderImplementor, NonBindingViewInflater.create(context));
    }

}
