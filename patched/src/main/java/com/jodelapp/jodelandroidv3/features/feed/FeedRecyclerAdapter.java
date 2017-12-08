package com.jodelapp.jodelandroidv3.features.feed;

import android.widget.TextView;

import com.jodelapp.jodelandroidv3.api.model.Post;

import java.util.List;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * Created by Admin on 08.12.2017.
 */

@SuppressWarnings({"ConstantConditions", "unused", "InfiniteRecursion"})
@DexEdit(contentOnly = true, defaultAction = DexAction.IGNORE)
public class FeedRecyclerAdapter {

    @SuppressWarnings("WeakerAccess")
    @DexIgnore
    static class PostViewHolder {
        @DexIgnore
        TextView cornerText;
    }

    @DexIgnore
    private List<Post> getPosts() {
        return null;
    }

    @DexWrap
    private void bindPostViewHolder(PostViewHolder postViewHolder, int i) {
        bindPostViewHolder(postViewHolder, i);
        Post mPost = getPosts().get(i);

        if (!mPost.isFromHome()) {
            postViewHolder.cornerText.setText(String.valueOf(mPost.getDistance()) + " km");
        }
    }
}
