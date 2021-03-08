package org.jeycode.multithreadsamples.chapter3;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Mail
{

      private final String message,destinationName,destinationMail;
}
