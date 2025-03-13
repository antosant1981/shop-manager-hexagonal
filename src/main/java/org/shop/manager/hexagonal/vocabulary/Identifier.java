package org.shop.manager.hexagonal.vocabulary;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.codec.StringCodec;
import com.github.f4b6a3.uuid.codec.UuidCodec;
import com.github.f4b6a3.uuid.codec.base.Base32Codec;
import com.github.f4b6a3.uuid.codec.other.SlugCodec;
import com.github.f4b6a3.uuid.exception.InvalidUuidException;

import java.util.Objects;
import java.util.UUID;

/**
 * A generic identifier for entities and aggregates.
 * <p>
 * This identifier uses a special variant of UUID's named COMBs.
 * https://www.informit.com/articles/printerfriendly/25862
 * <p>
 * The first part of the uuid is time-based, the rest of the UUID is randomized. This allows for proper indexing
 * when used as primary key in the database.
 * <p>
 * 1720b5c-bf10-423f-9b20-1f88a7e2763a
 * 01720b5c-bf10-43bf-a639-9b511243507f
 * 01720b5c-bf10-4b47-9925-f96f0e197ba5
 * 01720b5c-bf10-49ba-84c0-7ace9ae546bb
 * 01720b5c-bf10-4327-b62a-2a242dcc197f
 * 01720b5c-bf10-47cc-b631-4f262f500172
 * 01720b5c-bf10-42ca-b063-28d5329b8d90
 * 01720b5c-bf10-4442-876b-2710215d41e1
 * 01720b5c-bf11-4a08-a63f-4495659603b4 < millisecond changed
 * 01720b5c-bf11-49a8-8339-8e3b429fc6fb
 * 01720b5c-bf11-4879-81f9-97194a331e3f
 * 01720b5c-bf11-497f-a599-c52a8c692107
 * 01720b5c-bf11-403c-a1e1-4f112539f97a
 * 01720b5c-bf11-4e97-a9e3-59215d3d5fb1
 * 01720b5c-bf11-4135-bb7f-422ec3c33cca
 * 01720b5c-bf11-4460-9a62-bcf16e35b418
 * 01720b5c-bf11...
 * ^ look
 * <p>
 * |------------|---------------------|
 * prefix         randomness
 * <p>
 * Slugs can be created for these identifiers. Slugs are URL and filename friendly representations of the Identifier. This allows
 * the Identifier to be used inside URL's in a case-insensitive manner without special characters.
 * <p>
 * These identifiers are created using UUIDCreator. A small framework for dealing with UUID's.
 * More info on the used UUID variant can be found here: https://github.com/f4b6a3/uuid-creator/wiki/2.1.-Prefix-COMB
 */
public final class Identifier {

    private static final UuidCodec<String> slugCodec = new SlugCodec(new Base32Codec());
    private static final UuidCodec<String> stringCodec = new StringCodec();

    private final UUID uuid;

    private Identifier(UUID uuid) {
        this.uuid = uuid;
    }

    public static Identifier nextVal() {
        return new Identifier(UuidCreator.getPrefixComb());
    }

    public static Identifier fromString(String uuid) {
        try {
            return new Identifier(stringCodec.decode(uuid));
        } catch (InvalidUuidException e) {
            throw new InvalidException("Invalid value for identifier provided: " + uuid, e);
        }
    }

    public static Identifier fromSlug(String slug) {
        try {
            return new Identifier(slugCodec.decode(slug));
        } catch (InvalidUuidException e) {
            throw new InvalidException("Invalid value for identifier provided: " + slug, e);
        }
    }

    public String asString() {
        return stringCodec.encode(uuid);
    }

    public String asSlug() {
        return slugCodec.encode(uuid);
    }

    @Override
    public String toString() {
        return "Identifier{" +
                "uuid=" + uuid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Identifier that = (Identifier) o;
        return Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    public static class InvalidException extends RuntimeException {
        public InvalidException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
