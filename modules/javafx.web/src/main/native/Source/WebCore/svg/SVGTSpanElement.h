/*
 * Copyright (C) 2004, 2005, 2008 Nikolas Zimmermann <zimmermann@kde.org>
 * Copyright (C) 2004, 2005, 2006 Rob Buis <buis@kde.org>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public License
 * along with this library; see the file COPYING.LIB.  If not, write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301, USA.
 */

#pragma once

#include "SVGTextPositioningElement.h"

namespace WebCore {

class SVGTSpanElement final : public SVGTextPositioningElement {
    WTF_MAKE_TZONE_OR_ISO_ALLOCATED(SVGTSpanElement);
    WTF_OVERRIDE_DELETE_FOR_CHECKED_PTR(SVGTSpanElement);
public:
    static Ref<SVGTSpanElement> create(const QualifiedName&, Document&);

private:
    SVGTSpanElement(const QualifiedName&, Document&);

    RenderPtr<RenderElement> createElementRenderer(RenderStyle&&, const RenderTreePosition&) override;
    bool childShouldCreateRenderer(const Node&) const override;
    bool rendererIsNeeded(const RenderStyle&) override;
};

} // namespace WebCore
