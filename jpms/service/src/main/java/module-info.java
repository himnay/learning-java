module com.org.jpms.service {
    requires com.org.jpms.api;

    provides com.org.jpms.api.Greeter
            with com.org.jpms.service.EnglishGreeter,
                 com.org.jpms.service.SpanishGreeter;

    // com.org.jpms.service.internal is deliberately NOT exported: it's an
    // implementation detail, invisible to any other module at compile and
    // run time even though its classes are public.
}
