package com.wemeal.presentation.extensions

import android.content.Context
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.util.Log
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.wemeal.data.model.user.PlaceDataModel
import com.wemeal.presentation.util.events.CustomEvent
import com.wemeal.presentation.util.events.EventCase
import com.wemeal.presentation.util.placeFields
import kotlinx.coroutines.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Awaits for completion of the task without blocking a thread.
 *
 * This suspending function is cancellable.
 * If the Job of the current coroutine is cancelled or completed while this suspending function is waiting, this function
 * stops waiting for the completion stage and immediately resumes with [CancellationException].
 */
suspend fun <T> Task<T>.await(): T {
    // fast path
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException("Task $this was cancelled normally.")
            } else {
                @Suppress("UNCHECKED_CAST")
                result as T
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                @Suppress("UNCHECKED_CAST")
                if (isCanceled) cont.cancel() else cont.resume(result as T)
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}

suspend fun getAutocomplete(
    context: Context,
    constraint: CharSequence
): List<PlaceDataModel> {
    var list = listOf<AutocompletePrediction>()
    val address = mutableListOf<PlaceDataModel>()
    val token = AutocompleteSessionToken.newInstance()
    val request = FindAutocompletePredictionsRequest.builder()
        .setCountry("EG")
        .setSessionToken(token).setQuery(constraint.toString()).build()
    val mPlacesClient = Places.createClient(context)
    val prediction = mPlacesClient
        .findAutocompletePredictions(request)
    try {
        Tasks.await(prediction, 1000, TimeUnit.SECONDS)
    } catch (e: ExecutionException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    } catch (e: TimeoutException) {
        e.printStackTrace()
    }
    context.activity()?.logEvent(
        CustomEvent.USER_SEARCH_LOCATION,
        EventCase.ATTEMPT
    )

    if (prediction.isSuccessful) {
        val findAutocompletePredictionsResponse = prediction.result
        findAutocompletePredictionsResponse?.let {
            list = findAutocompletePredictionsResponse.autocompletePredictions
        }

        list.let {
            for (i in list.indices) {
                val item = list[i]
                address.add(
                    PlaceDataModel(
                        id = item.placeId,
                        name = item.getPrimaryText(StyleSpan(Typeface.BOLD)).toString(),
                        description = item.getFullText(StyleSpan(Typeface.NORMAL)).toString()
                    )
                )
            }
        }

        return address
    } else
        context.activity()?.logEvent(
            CustomEvent.USER_SEARCH_LOCATION,
            EventCase.FAILURE,
            message = prediction.exception?.message ?: ""
        )

    return address
}


fun getPlaceDetails(
    context: Context,
    place: PlaceDataModel,
    onSelect: (Place?) -> Unit
) {
    val placeId: String = java.lang.String.valueOf(place.id)
    val request = FetchPlaceRequest.builder(placeId, placeFields).build()
    val mPlacesClient = Places.createClient(context)
    mPlacesClient.fetchPlace(request)
        .addOnSuccessListener { response ->
            onSelect(response.place)
        }.addOnFailureListener { exception ->
            if (exception is ApiException) {
                exception.message?.let { Log.e("TAG", it) }
                onSelect(null)
            }
        }
}