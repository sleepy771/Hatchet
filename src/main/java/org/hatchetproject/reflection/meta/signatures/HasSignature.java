package org.hatchetproject.reflection.meta.signatures;

public interface HasSignature<SIGNATURE extends Signature> {
    SIGNATURE getSignature();
}
