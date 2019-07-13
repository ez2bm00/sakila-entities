package com.github.jinahya.sakila.persistence;

/*-
 * #%L
 * sakila-entities
 * %%
 * Copyright (C) 2019 Jinahya, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.persistence.AttributeOverride;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.github.jinahya.sakila.persistence.BaseEntity.ATTRIBUTE_NAME_ID;

/**
 * An entity class for binding {@value TABLE_NAME} table.
 */
@AttributeOverride(name = ATTRIBUTE_NAME_ID, column = @Column(name = Customer.COLUMN_NAME_CUSTOMER_ID))
@Entity
@Table(name = Customer.TABLE_NAME)
public class Customer extends BaseEntity {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * The target table name of this entity class. The value is {@value}.
     */
    public static final String TABLE_NAME = "customer";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_CUSTOMER_ID = "customer_id";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_STORE_ID = "store_id";

    public static final String ATTRIBUTE_NAME_STORE = "store";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String ATTRIBUTE_NAME_FULL_NAME = "fullName";

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-13 replace with FullName
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";

    // TODO: 2019-07-13 replace with FullName
    public static final String ATTRIBUTE_NAME_FIRST_NAME = "firstName";

    // TODO: 2019-07-13 replace with FullName
    public static final int SIZE_MIN_FIRST_NAME = 0;

    // TODO: 2019-07-13 replace with FullName
    public static final int SIZE_MAX_FIRST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------
    // TODO: 2019-07-13 replace with FullName
    public static final String COLUMN_NAME_LAST_NAME = "last_name";

    // TODO: 2019-07-13 replace with FullName
    public static final String ATTRIBUTE_NAME_LAST_NAME = "lastName";

    // TODO: 2019-07-13 replace with FullName
    public static final int SIZE_MIN_LAST_NAME = 0;

    // TODO: 2019-07-13 replace with FullName
    public static final int SIZE_MAX_LAST_NAME = 45;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_EMAIL = "email";

    public static final String ATTRIBUTE_NAME_EMAIL = "email";

    public static final int SIZE_MAX_EMAIL = 50;

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ADDRESS_ID = "address_id";

    public static final String ATTRIBUTE_NAME_ADDRESS = "address";

    // -----------------------------------------------------------------------------------------------------------------
    public static final String COLUMN_NAME_ACTIVE = "active";

    public static final String ATTRIBUTE_NAME_ACTIVE = "active";

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Creates a new instance.
     */
    public Customer() {
        super();
    }

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString() + "{"
               + "store=" + store
//               + ",fullName=" + fullName // TODO: 2019-07-13 uncomment
               + ",firstName=" + firstName // TODO: 2019-07-13 replace with fullName
               + ",lastName=" + lastName // TODO: 2019-07-13 replace with fullName
               + ",email=" + email
               + ",address=" + address
               + ",active=" + active
               + "}";
    }

    // TODO: 2019-07-10 Consider adding equals/hashCode.

    // ----------------------------------------------------------------------------------------------------------- store
    public Store getStore() {
        return store;
    }

    public void setStore(final Store store) {
        this.store = store;
    }

    // -------------------------------------------------------------------------------------------------------- fullName
    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(final FullName fullName) {
        this.fullName = fullName;
    }

    // ------------------------------------------------------------------------------------------------------- firstName
    // TODO: 2019-07-13 replace with FullName
    public String getFirstName() {
        return firstName;
    }

    // TODO: 2019-07-13 replace with FullName
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    // -------------------------------------------------------------------------------------------------------- lastName
    // TODO: 2019-07-13 replace with FullName
    public String getLastName() {
        return lastName;
    }

    // TODO: 2019-07-13 replace with FullName
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    // ----------------------------------------------------------------------------------------------------------- email
    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    // --------------------------------------------------------------------------------------------------------- address
    public Address getAddress() {
        return address;
    }

    public void setAddress(final Address address) {
        this.address = address;
    }

    // ---------------------------------------------------------------------------------------------------------- active
    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    // -----------------------------------------------------------------------------------------------------------------
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_STORE_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_STORE)
    private Store store;

    // TODO: 2019-07-13 uncomment @Embedded
    //@Embedded
    @NamedAttribute(ATTRIBUTE_NAME_FULL_NAME)
    private FullName fullName;

    // TODO: 2019-07-13 replace with FullName
    @Size(max = SIZE_MAX_FIRST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_FIRST_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_FIRST_NAME)
    private String firstName;

    // TODO: 2019-07-13 replace with FullName
    @Size(max = SIZE_MAX_LAST_NAME)
    @NotNull
    @Basic(optional = false)
    @Column(name = COLUMN_NAME_LAST_NAME, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_LAST_NAME)
    private String lastName;

    @Size(max = SIZE_MAX_EMAIL)
    @Basic
    @Column(name = COLUMN_NAME_EMAIL)
    @NamedAttribute(ATTRIBUTE_NAME_EMAIL)
    private String email;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = COLUMN_NAME_ADDRESS_ID, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ADDRESS)
    private Address address;

    @Basic(optional = false)
    @Column(name = COLUMN_NAME_ACTIVE, nullable = false)
    @NamedAttribute(ATTRIBUTE_NAME_ACTIVE)
    private boolean active;
}
