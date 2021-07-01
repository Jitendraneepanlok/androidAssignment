package com.tutorial.readallcontact.Activity

import android.R.attr
import android.content.ContentUris
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatSpinner
import com.tutorial.readallcontact.R


class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        var spin_address: AppCompatSpinner = findViewById(R.id.spin_address);
        val phoneTypeArr = arrayOf("Mobile", "Home", "Work")
        val phoneTypeSpinnerAdaptor = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            phoneTypeArr
        )
        spin_address.setAdapter(phoneTypeSpinnerAdaptor)
        val btn_save: AppCompatButton = findViewById(R.id.btn_save)

        btn_save.setOnClickListener {
            var et_name: AppCompatEditText = findViewById(R.id.et_name)
            var et_phone: AppCompatEditText = findViewById(R.id.et_phone)

            val addContactsUri: Uri = ContactsContract.Data.CONTENT_URI

            val rowContactId: Long = getRawContactId()
            val displayName: String = et_name.text.toString()
            insertContactDisplayName(addContactsUri, rowContactId, displayName)
            val phoneNumber: String = et_phone.text.toString()
            val phoneTypeStr = spin_address.getSelectedItem()
            insertContactPhoneNumber(addContactsUri, rowContactId, phoneNumber, phoneTypeStr);
            val toast = Toast.makeText(applicationContext, "Contact Added", Toast.LENGTH_LONG)
            toast.show()
            finish()
        }

    }

    private fun insertContactPhoneNumber(addContactsUri: Uri, rowContactId: Long, phoneNumber: String, phoneTypeStr: Any?) {
        // Create a ContentValues object.
        val contentValues = ContentValues()
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rowContactId)
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
        // Put phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, attr.phoneNumber)
        // Calculate phone type by user selection.
        var phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME
        if ("home".equals(phoneTypeStr == true)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME
        } else if ("mobile".equals(phoneTypeStr  == true)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
        } else if ("work".equals(phoneTypeStr == true)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_WORK
        }
        // Put phone type value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType)
        // Insert new contact data into phone contact list.
        contentResolver.insert(addContactsUri, contentValues)
    }

    private fun getRawContactId(): Long {
        // Inser an empty contact.
        val contentValues = ContentValues()
        val rawContactUri = contentResolver.insert(
            ContactsContract.RawContacts.CONTENT_URI,
            contentValues
        )
        // Get the newly created contact raw id.
        return ContentUris.parseId(rawContactUri!!)

    }

    private fun insertContactDisplayName(addContactsUri: Uri, rowContactId: Long, displayName: String) {
        val contentValues = ContentValues()
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rowContactId)
        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName)
        contentResolver.insert(addContactsUri, contentValues)
    }
}