package org.conggroup.vizard.dataconsumer.ringer;

import lombok.RequiredArgsConstructor;
import org.conggroup.vizard.dataconsumer.utils.SnowFlakeIdUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequiredArgsConstructor
public class RingerController {

    private final RingerProcessor ringerProcessor;

    private final SnowFlakeIdUtil snowFlakeIdUtil;

    @RequestMapping("/produceRingerQuery")
    public void produceRingerQuery(
            @RequestParam("ownerId") String ownerIdStr,
            @RequestParam("data") String dataStr) {
        Integer ownerId = Integer.parseInt(ownerIdStr);
        Long queryId = snowFlakeIdUtil.nextId();
        BigInteger data = new BigInteger(dataStr);

        ringerProcessor.produce(ownerId, queryId, data);
    }
}
