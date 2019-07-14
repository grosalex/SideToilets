package com.grosalex.sidetoilets;

import com.google.gson.Gson;
import com.grosalex.sidetoilets.contract.ToiletsContract;
import com.grosalex.sidetoilets.presenter.ToiletsPresenter;
import com.grosalex.sidetoilets.response.ToiletsResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class ToiletsPresenterTest {
    private ToiletsPresenter toiletsPresenter;
    @Mock
    private ToiletsContract.View view;
    @Mock
    private ToiletsContract.Provider provider;

    @Captor
    private ArgumentCaptor<ToiletsContract.Provider.OnToiletsFetched> onToiletsFetchedArgumentCaptor;
    @Captor
    private ArgumentCaptor<Boolean> trueCaptor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        toiletsPresenter = new ToiletsPresenter(view, provider);
    }

    @Test
    public void loadToilets() {

        toiletsPresenter.getToilets();
        verify(provider).getToilets(onToiletsFetchedArgumentCaptor.capture(), trueCaptor.capture());
        onToiletsFetchedArgumentCaptor.getValue().onSuccess(sampleResponse.getRecords());
        verify(view).onBindToiletsList(toiletsPresenter.convertListRecordToToiletData(sampleResponse.getRecords()));
    }

    private static final ToiletsResponse sampleResponse = (new Gson()).fromJson(FakeData.toiletListResponse, ToiletsResponse.class);
}
