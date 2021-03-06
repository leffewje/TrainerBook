package com.bignerdranch.android.trainerbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SessionListFragment extends Fragment {
    private RecyclerView mSessionRecyclerView;
    private SessionAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_list, container, false);

        mSessionRecyclerView = (RecyclerView) view.findViewById(R.id.session_recycler_view);
        mSessionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        SessionGroup sessionGroup = SessionGroup.get(getActivity());
        List<Session> sessions = sessionGroup.getSessions(); //mCustomer.getSessions();

        if (mAdapter == null) {
            mAdapter = new SessionAdapter(sessions);
            mSessionRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setSessions(sessions);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class SessionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Session mSession;
        private TextView mTitleTextView;
        private TextView mDateTextView;

        public SessionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_session, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.session_name);
            mDateTextView = (TextView) itemView.findViewById(R.id.session_date);
        }

        public void bind(Session session) {
            mSession = session;
            mTitleTextView.setText(mSession.getTitle());
            mDateTextView.setText(mSession.getDate().toString());
        }

        @Override
        public void onClick(View view) {
            Intent intent = SessionActivity.newIntent(getActivity(), mSession.getId());
            startActivity(intent);
        }
    }

    private class SessionAdapter extends RecyclerView.Adapter<SessionHolder> {

        private List<Session> mSessions;

        public SessionAdapter(List<Session> sessions) {
            mSessions = sessions;
        }

        @Override
        public SessionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new SessionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(SessionHolder holder, int position) {
            Session session = mSessions.get(position);
            holder.bind(session);
        }

        @Override
        public int getItemCount(){
            return mSessions.size();
        }

        public void setSessions(List<Session> sessions) {
            mSessions = sessions;
        }
    }
}
