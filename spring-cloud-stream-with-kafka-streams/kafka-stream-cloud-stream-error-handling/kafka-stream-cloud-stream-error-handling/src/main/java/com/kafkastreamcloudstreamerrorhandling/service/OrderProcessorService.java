package com.kafkastreamcloudstreamerrorhandling.service;

import com.kafkastreamcloudstreamerrorhandling.exception.InvalidCreditCardException;
import com.kafkastreamcloudstreamerrorhandling.model.OrderInputMsg;

public class OrderProcessorService {

    public static OrderInputMsg processOrderMsg(OrderInputMsg value) {
        value.setCreditCardNumber(maskCreditCardInformation(value));
        return value;
    }

    private static String maskCreditCardInformation(OrderInputMsg value) {
      String creditCardNumber = value.getCreditCardNumber();
      String[] split = creditCardNumber.split("_");
      for (String s:split)
      {
          if(s.length()!=4)
          {
              throw new InvalidCreditCardException();
          }
      }

        return "XXXX-XXXX-XXXX"+split[3];
    }
}
