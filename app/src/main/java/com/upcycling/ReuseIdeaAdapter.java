package com.upcycling;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;


public class ReuseIdeaAdapter extends RecyclerView.Adapter<ReuseIdeaAdapter.ViewHolder> {

    private List<ReuseIdea> reuseIdeas;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView reuseIdeaTextView, urlTextView;

        public ViewHolder(View view) {
            super(view);
            reuseIdeaTextView = view.findViewById(R.id.reuse_idea_text);
            urlTextView = view.findViewById(R.id.reuse_idea_url);
        }
    }

    public ReuseIdeaAdapter(List<ReuseIdea> reuseIdeas) {
        this.reuseIdeas = reuseIdeas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reuse_idea_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReuseIdea reuseIdea = reuseIdeas.get(position);
        SpannableStringBuilder builders = new SpannableStringBuilder();

        SpannableString nameSpannable = new SpannableString(reuseIdea.getName());
        nameSpannable.setSpan(new StyleSpan(Typeface.BOLD), 0, nameSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builders.append(nameSpannable);

        builders.append("\n");

        SpannableString descriptionSpannable = new SpannableString(reuseIdea.getDescription());
        descriptionSpannable.setSpan(new StyleSpan(Typeface.ITALIC), 0, descriptionSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builders.append(descriptionSpannable);

        holder.reuseIdeaTextView.setText(builders);

        //holder.urlTextView.setText(reuseIdea.getUrlInstructions());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(reuseIdea.getUrlInstructions()));
            v.getContext().startActivity(intent);
        });

        ImageView copyIcon = holder.itemView.findViewById(R.id.copy_icon);
        copyIcon.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) v.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("URL", reuseIdea.getUrlInstructions());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(v.getContext(), "URL copied to clipboard", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return reuseIdeas.size();
    }
}