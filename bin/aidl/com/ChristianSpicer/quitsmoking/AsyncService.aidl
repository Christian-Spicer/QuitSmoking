package com.ChristianSpicer.quitsmoking;

import com.ChristianSpicer.quitsmoking.RemoteService;

interface AsyncService {
		
    void FirstMainActivity(); // function called from activity
    void SecondMainActivity(); // function called from activity
    void registerCallBack(RemoteService cb);
    void unregisterCallBack(RemoteService cb);
}
