package org.jeycode.multithreadsamples.chapter4;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Mail
{

      private final String message,destinationName,destinationMail;
}
