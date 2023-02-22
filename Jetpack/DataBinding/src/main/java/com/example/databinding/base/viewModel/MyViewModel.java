package com.example.databinding.base.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private MutableLiveData<Integer> aTeamScore;
    private MutableLiveData<Integer> bTeamScore;
    private int aLast;
    private int bLast;

    public MutableLiveData<Integer> getATeamScore() {
        if(aTeamScore == null)
        {
            aTeamScore = new MutableLiveData<>();
            aTeamScore.setValue(0);
        }
        return aTeamScore;
    }

    public MutableLiveData<Integer> getBTeamScore() {
        if(bTeamScore == null)
        {
            bTeamScore = new MutableLiveData<>();
            bTeamScore.setValue(0);
        }
        return bTeamScore;
    }

    public void aTeamAdd(int score)
    {
        saveLastScore();
        aTeamScore.setValue(aTeamScore.getValue() + score);
    }

    public void bTeamAdd(int score)
    {
        saveLastScore();
        bTeamScore.setValue(bTeamScore.getValue() + score);
    }

    public void reset()
    {
        aTeamScore.setValue(aLast);
        bTeamScore.setValue(bLast);
    }

    public void clear()
    {
        aTeamScore.setValue(0);
        bTeamScore.setValue(0);
    }

    // 记录上一次的分数
    private void saveLastScore()
    {
        this.aLast = aTeamScore.getValue();
        this.bLast = bTeamScore.getValue();
    }
}
